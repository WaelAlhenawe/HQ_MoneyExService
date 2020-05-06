package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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


		System.out.println( "HQ MoneyService\n--------------------\n" );

		String SiteChoice =  HQApp_Support.SiteChoice();
		String StartDay_Period = HQApp_Support.StartDay_Period();

		String PeriodChoice = HQApp_Support.PeriodChoice();
		String statisticType = HQApp_Support.StatisticType();
		
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();
		String Currency = HQApp_Support.currencyChoice(ConExApp.availableCurrency(currencyMap));
		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), Currency);
		String path = HQApp_Support.filePath();

		HQ Statistic = new HQ_MoneyService(currencyMap);
		Statistic.filteredTran(req, path);

		if (statisticType.equals("Transactions")) {
			Statistic.printFilteredMap();
		}
		if (statisticType.equals("Profits")) {
			List<ProfitResult> temp = Statistic.profitStatistic(req);
			for (ProfitResult y : temp) {
				System.out.println(y);
			}
		}
		// End logging using a simple INFO message
		logger.fine("Test completed");

	}
}
