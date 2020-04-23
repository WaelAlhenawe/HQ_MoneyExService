package ya.java.effective.HQ_MoneyService;

import java.sql.Date;
import java.time.Duration;

public class Request {

	private final String site;
	private final Duration duration;
	private final Date date;
	private final String currency;
	/**
	 * @param site
	 * @param duration
	 * @param date
	 * @param currency
	 */
	public Request(String site, Duration duration, Date date, String currency) {
		super();
		this.site = site;
		this.duration = duration;
		this.date = date;
		this.currency = currency;
	}
	/**
	 * @return the site
	 */
	public synchronized String getSite() {
		return site;
	}
	/**
	 * @return the duration
	 */
	public synchronized Duration getDuration() {
		return duration;
	}
	/**
	 * @return the date
	 */
	public synchronized Date getDate() {
		return date;
	}
	/**
	 * @return the currency
	 */
	public synchronized String getCurrency() {
		return currency;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
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
		Request other = (Request) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Request [site=%s, duration=%s, date=%s, currency=%s]", site, duration, date, currency);
	} 
	
	
	
}
