package interview.beans;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class CountryRatingAverage implements Comparable<CountryRatingAverage> {
	private String country;
	private String rating;
	private BigDecimal totalAmount;
	private long totalEntries;

	public CountryRatingAverage(String country, String rating) {
		this.country = country;
		this.rating = rating;
	}

	public String getCountry() {
		return country;
	}

	public String getRating() {
		return rating;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(long totalEntries) {
		this.totalEntries = totalEntries;
	}
	
	
	public BigDecimal getAverage() {
		if (totalAmount == null || totalEntries <= 0) {
			return null;
		}
		return totalAmount.divide(new BigDecimal(totalEntries + ""));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryRatingAverage other = (CountryRatingAverage) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}

	@Override
	public int compareTo(CountryRatingAverage other) {
		if (other == null || StringUtils.isEmpty(other.getCountry())) {
			return -1;
		}

		if (StringUtils.isEmpty(this.getCountry())) {
			return 1;
		}

		int countryCompare = this.getCountry().compareTo(other.getCountry());
		if(countryCompare != 0){
			return countryCompare;
		}else{
			if(StringUtils.isEmpty(other.getRating())){
				return -1;
			}
			
			if(StringUtils.isEmpty(this.getRating())){
				return 1;
			}
			
			return this.getRating().compareTo(other.getRating());
		}
	}

	@Override
	public String toString() {
		return "CountryRatingAverage [country=" + country + ", rating=" + rating + ", totalAmount=" + totalAmount + ", totalEntries="
				+ totalEntries + "]";
	}

}
