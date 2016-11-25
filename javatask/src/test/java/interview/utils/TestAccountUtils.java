package interview.utils;

import org.junit.Assert;
import org.junit.Test;

import interview.exception.AccountException;

public class TestAccountUtils {
	@Test
	public void testGetConversionRate_NullArgs() {
		try {
			AccountUtils.getConversionRate(null, null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_NullFromCurrency() {
		try {
			AccountUtils.getConversionRate(null, CURRENCY_CODE.USD);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_NullToCurrency() {
		try {
			AccountUtils.getConversionRate(CURRENCY_CODE.USD, null);
			Assert.fail("Account Exception Should be thrown");
		} catch (AccountException e) {
			Assert.assertTrue("Account Exception Should be thrown", true);
		} catch (Exception exp) {
			Assert.fail("Only Account Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_SameCurrency() {
		try {
			Assert.assertEquals(new Double(1.0), AccountUtils.getConversionRate(CURRENCY_CODE.USD, CURRENCY_CODE.USD));
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_DiffCurrencyToUSD() {
		try {
			Assert.assertEquals(new Double(0.99), AccountUtils.getConversionRate(CURRENCY_CODE.CHF, CURRENCY_CODE.USD));
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_USDToDiffCurrency() {
		try {
			Assert.assertEquals("1.0101", AccountUtils.getConversionRate(CURRENCY_CODE.USD, CURRENCY_CODE.CHF).toString().substring(0, 6));
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}

	@Test
	public void testGetConversionRate_BetweenDiffCurrencies() {
		try {
			Assert.assertEquals("1.2575", AccountUtils.getConversionRate(CURRENCY_CODE.GBP, CURRENCY_CODE.CHF).toString().substring(0, 6));
		} catch (AccountException e) {
			Assert.fail("Account Exception Should Not be thrown");
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}

	// padString(String input, int padLen)

	@Test
	public void testPadString_NullString() {
		try {
			Assert.assertEquals("", AccountUtils.padString(null, 0));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}

	@Test
	public void testPadString_NullStringWithPad() {
		try {
			Assert.assertEquals("   ", AccountUtils.padString(null, 3));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}
	
	@Test
	public void testPadString_EmptyStringWithPad() {
		try {
			Assert.assertEquals("   ", AccountUtils.padString("", 3));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}
	
	@Test
	public void testPadString_StringWithNegativePad() {
		try {
			Assert.assertEquals("", AccountUtils.padString("Hello", -3));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}
	
	@Test
	public void testPadString_BigStringWithSmallPad() {
		try {
			Assert.assertEquals("He", AccountUtils.padString("Hello", 2));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}
	
	@Test
	public void testPadString_SmallStringWithBigPad() {
		try {
			Assert.assertEquals("Hello     ", AccountUtils.padString("Hello", 10));
		} catch (Exception exp) {
			Assert.fail("Exception Should be thrown");
		}
	}
}
