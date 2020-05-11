/**
 * 
 */
package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * ProfitResult is a value type defining a collectable item to be stored in a HQ
 * 
 * 
 */ 
 public class ProfitResult {
	final int buy;
	final int sell;
	final LocalDate timeStamp;
	final int profit;
	final String siteName;
	final String currencyCode;
	
	
	
	
	/**
	 * @param buy The amount of buying currency
	 * @param sell The amount of selling currency
	 * @param timeStamp Date of the transaction 
	 * @param profit The amount of profit for specific currency in specific day 
	 * @param siteName the name of site for each transaction 
	 * @param currencyCode the currency code for this transaction 
	 */
	public ProfitResult(int buy, int sell, LocalDate timeStamp, String siteName, String currencyCode) {
		super();
		this.buy = buy;
		this.sell = sell;
		this.timeStamp = timeStamp;
		this.profit = sell - buy;
		this.siteName = siteName;
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the buy
	 */
	public synchronized int getBuy() {
		return buy;
	}
	/**
	 * @return the sell
	 */
	public synchronized int getSell() {
		return sell;
	}
	/**
	 * @return the timeStamp
	 */
	public synchronized LocalDate getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @return the profit
	 */
	public synchronized int getProfit() {
		return profit;
	}
	/**
	 * @return the siteName
	 */
	public synchronized String getSiteName() {
		return siteName;
	}
	/**
	 * @return the currencyCode
	 */
	public synchronized String getCurrencyCode() {
		return currencyCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + buy;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + profit;
		result = prime * result + sell;
		result = prime * result + ((siteName == null) ? 0 : siteName.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		ProfitResult other = (ProfitResult) obj;
		if (buy != other.buy)
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (profit != other.profit)
			return false;
		if (sell != other.sell)
			return false;
		if (siteName == null) {
			if (other.siteName != null)
				return false;
		} else if (!siteName.equals(other.siteName))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%nStatistics for site %s - Currency %s  - Date %s %n Total  SELL\t %d \tSEK %n Total   BUY\t %d \tSEK %n Profit    \t %d \tSEK%n",
				siteName, currencyCode, (DateTimeFormatter.ISO_DATE) 
				.format(timeStamp),  sell, buy, profit);
	}
	
	
	
	
}