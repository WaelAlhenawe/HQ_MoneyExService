package ya.java.effective.HQ_MoneyService;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

/**
 * @author wael + robin & batman and mr Boba & SuperMan
 *
 */
public class HQ_App 
{
	static Map<String, Double> currencyMap = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt").orElse(Collections.emptyMap());
	//	private static Logger logger;
	//	static List<Transaction> t = new ArrayList<Transaction>();
	static Scanner input = new Scanner(System.in);

	public static void main( String[] args )
	{
		System.out.println( "HQ MoneyService\n---------------------\n" );

		String SiteChoice =  HQApp_Support.SiteChoice();
		String PeriodChoice = HQApp_Support.PeriodChoice();
		String StartDay_Period = HQApp_Support.StartDay_Period();
		String Currency = HQApp_Support.currencyChoice(currencyMap);
		
		
		HQApp_Support.printingChoiceForStats(SiteChoice,PeriodChoice,StartDay_Period,Currency);
		
		HQ_MoneyService.searchAndPrintFiles(SiteChoice, PeriodChoice, StartDay_Period, Currency);


	}



}
