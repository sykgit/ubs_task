package interview.parser;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import interview.beans.AccountDetails;
import interview.beans.CountryRatingAverage;
import interview.exception.AccountException;
import interview.utils.CURRENCY_CODE;

public class TestFileAccountDataParser {

	private String getValidFile() throws FileNotFoundException, URISyntaxException {
		String str = Paths.get(ClassLoader.getSystemResource("FILE.DAT").toURI()).toFile().getAbsolutePath();
		if (StringUtils.isEmpty(str)) {
			throw new FileNotFoundException(str);
		}
		return str;
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_NullArgs() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile(null, null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_NullFile() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile(null, CURRENCY_CODE.EUR);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_EmptyFile() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile("", CURRENCY_CODE.EUR);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_InvalidFile() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile("qwsrqe", CURRENCY_CODE.EUR);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_ValidFile() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile(getValidFile(), CURRENCY_CODE.EUR);
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should Not be thrown");
		}
	}

	@Test
	public void testGetCountryRatingAveragesFromFile_ValidFileNullCurrency() {
		try {
			FileAccountDataParser.getCountryRatingAveragesFromFile(getValidFile(), null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_NullArgs() {
		try {
			FileAccountDataParser.generateAccountDetails(null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_EmptyArgs() {
		try {
			FileAccountDataParser.generateAccountDetails("");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_InvalidArgs() {
		try {
			FileAccountDataParser.generateAccountDetails("SDAFFD");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_EmptyCountryAndCity() {
		try {
			FileAccountDataParser.generateAccountDetails("2318	8084107			Aaa+	CHF	836211889.9");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_EmptyCurrency() {
		try {
			FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore	IND	Aaa+		836211889.9");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_EmptyAmount() {
		try {
			FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore	IND	Aaa+	CHF	");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_InvalidAmount() {
		try {
			FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore	IND	Aaa+	CHF	214y23421");
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_ValidLineAllFields() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore	IND	Aaa+	CHF	836211889.9");
			AccountDetails account = new AccountDetails();
			account.setCompanyCode(2318 + "");
			account.setAccountNo(8084107 + "");
			account.setCity("Bangalore");
			account.setCountry("IND");
			account.setCreditRating("Aaa+");
			account.setCurrencyCode(CURRENCY_CODE.getCode("CHF"));
			account.setAmount(new BigDecimal("836211889.9"));
			Assert.assertEquals(account, accountFromFile);
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should Not be thrown");
		}
	}

	@Test
	public void testGenerateAccountDetails_ValidLineEmptyCountry() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	836211889.9");
			AccountDetails account = new AccountDetails();
			account.setCompanyCode(2318 + "");
			account.setAccountNo(8084107 + "");
			account.setCity("Bangalore");
			account.setCountry("");
			account.setCreditRating("Aaa+");
			account.setCurrencyCode(CURRENCY_CODE.getCode("CHF"));
			account.setAmount(new BigDecimal("836211889.9"));
			Assert.assertEquals(account, accountFromFile);
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should Not be thrown");
		}
	}

	// computeAverage(AccountDetails account, HashMap<CountryRatingAverage, CountryRatingAverage> avgMap,CURRENCY_CODE toCurrency)

	@Test
	public void testComputeAverage_NullArgs() {
		try {
			FileAccountDataParser.computeAverage(null, null, null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_NullAccount() {
		try {
			FileAccountDataParser.computeAverage(null, new HashMap<>(), CURRENCY_CODE.EUR);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_NullAvgMap() {
		try {
			FileAccountDataParser.computeAverage(new AccountDetails(), null, CURRENCY_CODE.EUR);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_NullCurrency() {
		try {
			FileAccountDataParser.computeAverage(new AccountDetails(), new HashMap<>(), null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_SingleEntry() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	836211889.9");
			HashMap<CountryRatingAverage, CountryRatingAverage> avgMap = new HashMap<>();

			FileAccountDataParser.computeAverage(accountFromFile, avgMap, CURRENCY_CODE.CHF);

			Assert.assertEquals(1, avgMap.size());

			ArrayList<CountryRatingAverage> ratingAverages = new ArrayList<>();
			ratingAverages.addAll(avgMap.values());

			Assert.assertEquals("836211889.9", ratingAverages.get(0).getAverage().toString().substring(0, 11));

		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_MultipleEntrys() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	100");

			AccountDetails accountFromFile1 = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	200");

			HashMap<CountryRatingAverage, CountryRatingAverage> avgMap = new HashMap<>();

			FileAccountDataParser.computeAverage(accountFromFile, avgMap, CURRENCY_CODE.CHF);
			FileAccountDataParser.computeAverage(accountFromFile1, avgMap, CURRENCY_CODE.CHF);

			Assert.assertEquals(1, avgMap.size());

			ArrayList<CountryRatingAverage> ratingAverages = new ArrayList<>();
			ratingAverages.addAll(avgMap.values());

			Assert.assertEquals("150", ratingAverages.get(0).getAverage().toString().substring(0, 3));

		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_MultipleEntrysDiffCurrencies() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	100");

			AccountDetails accountFromFile1 = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore		Aaa+	GBP	200");

			HashMap<CountryRatingAverage, CountryRatingAverage> avgMap = new HashMap<>();

			FileAccountDataParser.computeAverage(accountFromFile, avgMap, CURRENCY_CODE.EUR);
			FileAccountDataParser.computeAverage(accountFromFile1, avgMap, CURRENCY_CODE.EUR);

			Assert.assertEquals(1, avgMap.size());

			ArrayList<CountryRatingAverage> ratingAverages = new ArrayList<>();
			ratingAverages.addAll(avgMap.values());

			Assert.assertEquals("158.18", ratingAverages.get(0).getAverage().toString().substring(0, 6));
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testComputeAverage_MultipleEntrysMultipleCountrysDiffCurrencies() {
		try {
			AccountDetails accountFromFile = FileAccountDataParser.generateAccountDetails("2318	8084107	Bangalore		Aaa+	CHF	100");

			AccountDetails accountFromFile1 = FileAccountDataParser
					.generateAccountDetails("2318	8084107	Bangalore		Aaa+	GBP	200");

			AccountDetails accountFromFile2 = FileAccountDataParser
					.generateAccountDetails("2303	2954026	Atlanta	USA	AAA+	CHF	65484231.44");

			HashMap<CountryRatingAverage, CountryRatingAverage> avgMap = new HashMap<>();

			FileAccountDataParser.computeAverage(accountFromFile, avgMap, CURRENCY_CODE.EUR);
			FileAccountDataParser.computeAverage(accountFromFile1, avgMap, CURRENCY_CODE.EUR);
			FileAccountDataParser.computeAverage(accountFromFile2, avgMap, CURRENCY_CODE.EUR);

			Assert.assertEquals(2, avgMap.size());

		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}
}
