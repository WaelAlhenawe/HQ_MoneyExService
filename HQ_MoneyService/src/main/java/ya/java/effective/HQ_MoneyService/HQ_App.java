package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
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
//	static Map<String, Double> currencyMap = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt").orElse(Collections.emptyMap());
	
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

		
		System.out.println( "HQ MoneyService\n---------------------\n" );

		String SiteChoice =  HQApp_Support.SiteChoice();
		String PeriodChoice = HQApp_Support.PeriodChoice();
		String StartDay_Period = HQApp_Support.StartDay_Period();
		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();
		
		String Currency = HQApp_Support.currencyChoice(currencyMap.get(LocalDate.parse(StartDay_Period)) );
		String path = HQApp_Support.filePath();
		LocalDate date = LocalDate.parse("2020-04-20");

		HQ x = new HQ_MoneyService(currencyMap);

		x.filteredTran(req, "..//");

		System.out.println(currencyMap);

		x.printFilteredMap("..//");
		x.profitStatistic(req);
		
//		HQApp_Support.printingChoiceForStats(SiteChoice,PeriodChoice,StartDay_Period,Currency);
		
//		HQ_MoneyService.searchFilter(SiteChoice, PeriodChoice, StartDay_Period, Currency, path);

		
		
		// End logging using a simple INFO message
				logger.fine("Test completed");
		
	}



}
