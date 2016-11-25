package interview.beans;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class TestCountryRatingAverage {

	@Test
	public void testGetAverage_NullArgs() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		Assert.assertNull(avgBean.getAverage());
	}

	@Test
	public void testGetAverage_NullAmount() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		avgBean.setTotalEntries(2);
		Assert.assertNull(avgBean.getAverage());
	}

	@Test
	public void testGetAverage_ZeroEntries() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		avgBean.setTotalEntries(0);
		avgBean.setTotalAmount(new BigDecimal("100"));
		Assert.assertNull(avgBean.getAverage());
	}

	@Test
	public void testGetAverage_NegativeEntries() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		avgBean.setTotalEntries(-2);
		avgBean.setTotalAmount(new BigDecimal("100"));
		Assert.assertNull(avgBean.getAverage());
	}

	@Test
	public void testGetAverage_NegativeAmount() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		avgBean.setTotalEntries(2);
		avgBean.setTotalAmount(new BigDecimal("-100"));
		Assert.assertEquals(new BigDecimal("-50"), avgBean.getAverage());
	}

	@Test
	public void testGetAverage_PostiveAmountAndEntries() {
		CountryRatingAverage avgBean = new CountryRatingAverage(null, null);
		avgBean.setTotalEntries(2);
		avgBean.setTotalAmount(new BigDecimal("100"));
		Assert.assertEquals(new BigDecimal("50"), avgBean.getAverage());
	}
}
