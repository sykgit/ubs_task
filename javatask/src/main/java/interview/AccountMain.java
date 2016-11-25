package interview;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

import interview.beans.CountryRatingAverage;
import interview.exception.AccountException;
import interview.parser.FileAccountDataParser;
import interview.utils.AccountUtils;
import interview.utils.CURRENCY_CODE;

/**
 * 
 * @author skuppuraju
 *
 */
public class AccountMain {

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Please provide Data file location:");
			String fileName = scanner.nextLine();
			scanner.close();
			CURRENCY_CODE currency = CURRENCY_CODE.EUR;

			Set<CountryRatingAverage> countryRatingAverages = FileAccountDataParser.getCountryRatingAveragesFromFile(fileName, currency);
			printHeader();
			if (countryRatingAverages != null && countryRatingAverages.size() > 0) {
				for (CountryRatingAverage avgBean : countryRatingAverages) {
					printBean(avgBean);
				}
			} else {
				System.out.println(" ---------------- NO DATA AVAILABLE -------------");
			}

		} catch (AccountException exp) {
			exp.printStackTrace();
		}

	}

	private static void printBean(CountryRatingAverage avgBean) {
		StringBuilder output = new StringBuilder();
		output.append(AccountUtils.padString(avgBean.getCountry(), 13));
		output.append("\t");
		output.append(AccountUtils.padString(avgBean.getRating(), 13));
		output.append("\t\t");
		BigDecimal average = avgBean.getAverage();
		output.append((average == null) ? "" : average.toString());
		System.out.println(output.toString());

	}

	private static void printHeader() {
		System.out.println("\n");
		System.out.println("Country      \tCredit Rating\t\tAverage");
		System.out.println("_______________________________________________________");
	}

}
