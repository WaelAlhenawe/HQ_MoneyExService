
package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 
 * This is the interface HQ
 * @author Team South
 *
 */
public interface HQ {

	/**
	 * This method is used for Collect all the necessary information, and details   
	 * @param request holding site, date from, duration, Currency with (All) available as a choice  
	 * @return An optional map with the site name as a key and List of transactions as values.
	 */
	public void filteredTran (Request request, String location);
	
	/**
	 * This method is used to print Transaction statistic by printing the Map of filtered values 
	 */
	public void printFilteredMap();

	/**
	 *	This method is used to build a List of ProfitResult object for profit type of statistic for each currency each day.
	 * @param request holding site, date from, duration, Currency with (All) available as a choice  
	 * @return Set of ProfitResult object for profit type of statistic for each currency each day.
	 */
	public Set<ProfitResult> profitStatistic();



	default void printSummarizeProfitStatistic(ProfitStatisticMode period, Set<ProfitResult> temp, LocalDate endDate,
			LocalDate startDate) {
	}











}



