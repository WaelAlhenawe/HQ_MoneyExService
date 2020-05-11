package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * This is a support class to handle requests
 * @author Team South
 *
 */
public class Request {

	private List<String> site = new ArrayList<>();
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final String currency;
	private final ProfitStatisticMode presentingMode;

	// Set up a logger
	private static Logger logger;

	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}

	/**
	 * Constructor
	 * @param site a String defining the site of choice
	 * @param duration a String defining the period
	 * @param date a LocalDate defining the date
	 * @param currency a String defining the currency code (ie USD)
	 * conditions if site choice is set to ALL, config should be read for configuration
	 */
	public Request(String site, LocalDate endDate, LocalDate date, String currency, ProfitStatisticMode presentingMode) {
		super();
		if (site.equals("ALL")){
			this.site = ConExApp.readSiteNamesConfig("SiteNamesConfig.txt").get();
		}else {
			this.site.add(site);		}
		this.endDate = endDate;
		this.startDate = date;
		this.currency = currency;
		this.presentingMode = presentingMode;
		logger.finer("A request Object has been created");
	}
	/**
	 * @return the site
	 */
	public List<String> getSite() {
		return site;
	}
	/**
	 * @return the duration
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	/**
	 * @return the currency
	 */
	public synchronized String getCurrency() {
		return currency;
	}
	/**
	 * @return the startDate
	 */
	public synchronized LocalDate getStartDate() {
		return startDate;
	}
	/**
	 * @return the presentingMode
	 */
	public synchronized ProfitStatisticMode getPresentingMode() {
		return presentingMode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((presentingMode == null) ? 0 : presentingMode.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (presentingMode != other.presentingMode)
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Request [site=%s, startDate=%s, endDate=%s, currency=%s, presentingMode=%s]", site,
				startDate, endDate, currency, presentingMode);
	}
	
	
}
