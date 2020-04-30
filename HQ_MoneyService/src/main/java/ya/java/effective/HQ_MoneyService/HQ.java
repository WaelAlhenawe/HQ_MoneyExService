
package ya.java.effective.HQ_MoneyService;

import java.util.List;

/**
 * 
 * This is the interface HQ
 * @author Team South
 *
 */
public interface HQ {

	/**
	 * This method is used for Collect all the necessary information, and details   
	 * @param staticrequest holding site, date from, duration, Currency with (All) available as a choice  
	 * @return An optional map with the site name as a key and List of transactions as values.
	 */
	public void filteredTran (Request staticRequest, String location);
	
	/**
	 * This method is used to print a report presenting current currencies and their amount
	 * for an implementation of the MoneyService interface, i.e. a Site
	 * @param destination a String defining where to write the report, i.e. Console/Textfile
	 */
	public void printFilteredMap(String destination);

	/**
	 * This method shuts down the service properly, i.e. closing server/db connection, 
	 * storing data for all completed orders for future recovery etc
	 * @param destination a String defining an optional destination for back-up data
	 * @return 
	 */
	public List<ProfitResult> profitStatistic(Request request);













}



