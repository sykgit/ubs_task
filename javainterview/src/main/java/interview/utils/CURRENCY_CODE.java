package interview.utils;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public enum CURRENCY_CODE {
	USD, GBP, CHF, EUR;

	private static HashMap<String, CURRENCY_CODE> currencyCodes = new HashMap<>();

	static {
		currencyCodes.put("usd", CURRENCY_CODE.USD);
		currencyCodes.put("gbp", CURRENCY_CODE.GBP);
		currencyCodes.put("chf", CURRENCY_CODE.CHF);
		currencyCodes.put("eur", CURRENCY_CODE.EUR);
	}

	public static CURRENCY_CODE getCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		} else {
			return currencyCodes.get(code.trim().toLowerCase());
		}
	}
}
