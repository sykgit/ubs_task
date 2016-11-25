package interview.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interview.beans.AccountDetails;
import interview.beans.CountryRatingAverage;
import interview.exception.AccountException;
import interview.exception.ERROR_CODE;
import interview.utils.AccountUtils;
import interview.utils.CURRENCY_CODE;

/**
 * 
 * @author skuppuraju
 *
 */
public class FileAccountDataParser {
	private static final Logger logger = LogManager.getLogger(FileAccountDataParser.class);

	protected FileAccountDataParser() {
	}

	public static Set<CountryRatingAverage> getCountryRatingAveragesFromFile(String fileName, CURRENCY_CODE toCurrency)
			throws AccountException {
		if (fileName == null) {
			throw AccountException.generateException(ERROR_CODE.NULL_FILE);
		}

		if (toCurrency == null) {
			throw AccountException.generateException(ERROR_CODE.NULL_CURRENCY);
		}

		try {
			logger.debug("Parsing File: " + fileName);
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			boolean firstLine = true;
			int lineNo = 1;

			HashMap<CountryRatingAverage, CountryRatingAverage> resultMap = new HashMap<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (firstLine) {
					firstLine = false;
				} else {

					/**
					 * Note that in the below case the account details can be fetched and stored. However by doing that - we may end up in
					 * OOM exception for the cases where the file size is very large.
					 * 
					 * The Scanner class we are using here internally uses buffered reader and does not load the full file. So there are no
					 * chances of OOM in this case.
					 * 
					 * The choice of whether AccountDetails are to be stored and later computed (OR) data to be dynamically computed depends
					 * on the design.
					 * 
					 * I focused on the aspect of code correctness and not fail even on the extreme scenarios where the file size is in
					 * GB's.
					 */
					logger.debug("Reading LineNo:" + lineNo);
					AccountDetails account = generateAccountDetails(line);
					computeAverage(account, resultMap, toCurrency);
					logger.debug("Done Reading LineNo:" + lineNo);
				}
				lineNo++;
			}
			scanner.close();

			TreeSet<CountryRatingAverage> sortedSet = new TreeSet<>();
			sortedSet.addAll(resultMap.values());

			return sortedSet;
		} catch (FileNotFoundException e) {
			throw AccountException.generateExceptionFromThrowable(ERROR_CODE.FILE_NOT_FOUND, e);
		}

	}

	protected static AccountDetails generateAccountDetails(String line) throws AccountException {
		if (StringUtils.isEmpty(line)) {
			throw AccountException.generateException(ERROR_CODE.INSUFFICIENT_DATA);
		}

		String tokens[] = line.split("\t");

		if (tokens == null || tokens.length < 7) {
			throw AccountException.generateException(ERROR_CODE.INSUFFICIENT_DATA);
		}

		AccountDetails account = new AccountDetails();
		account.setCompanyCode(tokens[0]);
		account.setAccountNo(tokens[1]);
		account.setCity(tokens[2]);
		account.setCountry(tokens[3]);
		account.setCreditRating(tokens[4]);
		CURRENCY_CODE currencyCode = CURRENCY_CODE.getCode(tokens[5]);
		if (currencyCode == null) {
			throw AccountException.generateException(ERROR_CODE.CURR_NOT_FOUND, tokens[5]);
		}

		account.setCurrencyCode(currencyCode);
		try {
			BigDecimal bd = new BigDecimal(tokens[6]);
			account.setAmount(bd);
		} catch (Exception exp) {
			throw AccountException.generateException(ERROR_CODE.INVALID_AMOUNT_FIELD);
		}

		if (StringUtils.isEmpty(account.getCountry()) && StringUtils.isEmpty(account.getCity())) {
			throw AccountException.generateException(ERROR_CODE.NULL_COUNTRY_CITY);
		}

		return account;
	}

	protected static void computeAverage(AccountDetails account, HashMap<CountryRatingAverage, CountryRatingAverage> avgMap,
			CURRENCY_CODE toCurrency) throws AccountException {
		if (account == null) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "account is Null");
		}

		if (avgMap == null) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "avgMap is Null");
		}

		if (toCurrency == null) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "toCurrency is Null");
		}

		CountryRatingAverage resultObj = new CountryRatingAverage(
				StringUtils.isEmpty(account.getCountry()) ? account.getCity() : account.getCountry(), account.getCreditRating());

		BigDecimal amtInReqCurrency = account.getAmount()
				.multiply(new BigDecimal("" + AccountUtils.getConversionRate(account.getCurrencyCode(), toCurrency)));

		if (avgMap.get(resultObj) != null) {
			resultObj = avgMap.get(resultObj);
			resultObj.setTotalAmount(resultObj.getTotalAmount().add(amtInReqCurrency));
			resultObj.setTotalEntries(resultObj.getTotalEntries() + 1);
		} else {
			resultObj.setTotalEntries(1);
			resultObj.setTotalAmount(amtInReqCurrency);
		}
		avgMap.put(resultObj, resultObj);
	}

}
