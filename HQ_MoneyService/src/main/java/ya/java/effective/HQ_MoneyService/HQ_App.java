package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;



/**
 * 
 * This is main class for HQ App
 * @author Team South
 *
 */
public class HQ_App 
{
	static Scanner input = new Scanner(System.in);

	// Set up a logger
	private static Logger logger;

	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}

	public static void main( String[] args )
	{
		// Set up a logger
		LogHelper.buildLog();

		// Start logging using a simple INFO message
		logger.fine("Starting test");  

		ProfitStatisticMode presentingMode = null;
		System.out.println( "HQ MoneyService\n--------------------\n" );

		String siteChoice =  HQApp_Support.SiteChoice();
		String statisticType = HQApp_Support.StatisticType();
		LocalDate StartDay_Period = HQApp_Support.StartDay_Period();

		LocalDate endDate = HQApp_Support.PeriodChoice(StartDay_Period);
		
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(endDate, StartDay_Period, "..//").get();
		
		String currency = HQApp_Support.currencyChoice(ConExApp.availableCurrency(currencyMap));
		if ( statisticType.equals("Profits")) {
			presentingMode = HQApp_Support.profitStatisticMode();
		}
		Request req = new Request(siteChoice, endDate,StartDay_Period, currency, presentingMode);
		String path = HQApp_Support.filePath();

		HQ Statistic = new HQ_MoneyService(currencyMap);
		Statistic.filteredTran(req, path);

		if (statisticType.equals("Transactions")) {
			Statistic.printFilteredMap();
		}
		if (statisticType.equals("Profits")) {
			Set<ProfitResult> temp = Statistic.profitStatistic();
//			for (ProfitResult y : temp) {
//				System.out.println(y);
//				
//			}
			Statistic.printSummarizeProfitStatistic(presentingMode, temp,endDate,StartDay_Period);
		}
		// End logging using a simple INFO message
		logger.fine("Test completed");

	}
}
