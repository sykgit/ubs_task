package interview.utils;

import java.util.HashMap;

import interview.exception.AccountException;
import interview.exception.ERROR_CODE;

/**
 * 
 * @author skuppuraju
 *
 */
public final class AccountUtils {
	private static HashMap<CURRENCY_CODE, Double> currencyConversionToUSDMap = new HashMap<>();

	static {
		updateCurrencyMap();
	}

	private AccountUtils() {
		updateCurrencyMap();
	}

	private static void updateCurrencyMap() {
		/**
		 * TODO: Generally currencies are fetched once a day from external source/designated table and they are stored in the cache. This is
		 * again dependent as how the application is being designed.
		 * 
		 * For this interview-task, I am hard-coding the currency conversion values as given in the requirement document.
		 */

		currencyConversionToUSDMap.put(CURRENCY_CODE.GBP, 1.245);
		currencyConversionToUSDMap.put(CURRENCY_CODE.CHF, 0.99);
		currencyConversionToUSDMap.put(CURRENCY_CODE.EUR, 1.10);
		currencyConversionToUSDMap.put(CURRENCY_CODE.USD, 1.0);
	}

	public static Double getConversionRate(CURRENCY_CODE fromCurrency, CURRENCY_CODE toCurrency) throws AccountException {
		if (fromCurrency == null || toCurrency == null) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "getConversionRateToUSD: Input Currency Codes NULL",
					"" + fromCurrency, "" + toCurrency);
		}

		if (fromCurrency == toCurrency) {
			return 1.0;
		}

		Double fromCurrInUSD = currencyConversionToUSDMap.get(fromCurrency);

		if (fromCurrInUSD == null || fromCurrInUSD == 0.0) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "GIVEN Currency Code not configured in system",
					"" + fromCurrency);
		}

		Double toCurrInUSD = currencyConversionToUSDMap.get(toCurrency);

		if (toCurrInUSD == null || toCurrInUSD == 0.0) {
			throw AccountException.generateException(ERROR_CODE.APPL_EXCEPTION, "GIVEN Currency Code not configured in system",
					"" + toCurrInUSD);
		}

		Double conversionRate = (1 / toCurrInUSD) * fromCurrInUSD;
		return conversionRate;
	}

	public static String padString(String input, int padLen) {
		if (padLen > 0) {
			if (input == null) {
				input = "";
			}
			int spacesToAdd = padLen - input.length();

			if (spacesToAdd <= 0) {
				return input.substring(0, padLen);
			} else {
				StringBuilder output = new StringBuilder();
				output.append(input);
				for (int i = 0; i < spacesToAdd; i++) {
					output.append(" ");
				}
				return output.toString();
			}

		} else {
			return "";
		}
	}
}
