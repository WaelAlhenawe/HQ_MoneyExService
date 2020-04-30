package ya.java.effective.HQ_MoneyService;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * This is a support class for user interaction using CLI
 * @author Team South
 *
 */
public class HQApp_Support {

	static Scanner input = new Scanner(System.in);
	static {
		input.useDelimiter(System.getProperty("line.separator"));
	}

	   // Set up a logger
		private static Logger logger;

		static{
			logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
		}
		
	static String SiteChoice() {
		
		String north = "NORTH";
		String center = "CENTER";
		String south = "SOUTH";
		String west = "WEST";

		String site = null;
		
		int choice = 0;
		boolean ok;
		do {
			ok = true;
			System.out.println("Choose a Site ");
			System.out.println("1 - NORTH");
			System.out.println("2 - CENTER");
			System.out.println("3 - SOUTH");
			System.out.println("4 - WEST");

			System.out.println("5 - ALL");

			System.out.print("Enter Your Choice:\t");
			String Site_choice = input.next();
			try {
				choice = Integer.parseInt(Site_choice);
			}catch(NumberFormatException e) {
				System.out.println("Your choice " + Site_choice + " is not accepted!");
				ok = false;
			}

		}while(!ok); 
		
		if(choice == 1) {
			site = north;
		} if(choice == 2) {
			site = center;
		} if(choice == 3) {
			site = south;
		} if(choice == 4) {
			site = west;
		} if(choice == 5) {
			site = "ALL";
		}
		
				
		return site;
	}

	static String PeriodChoice() {

		String day = "DAY";
		String week = "WEEK";
		String month = "MONTH";
		String period = null;
		
		int choice =0;
		boolean ok;
		do {
			ok = true;

			System.out.println("\nChoose a Period ");
			System.out.println("1 - DAY ");
			System.out.println("2 - WEEK");
			System.out.println("3 - MONTH");

			System.out.print("Enter Your Choice:\t");
			String Period_choice = input.next();
			try {
				choice = Integer.parseInt(Period_choice);
			}catch(NumberFormatException e) {
				System.out.println("Your choice " + Period_choice + " is not accepted!");
				ok = false;
			}

		}while(!ok); 
		
		if(choice == 1) {
			period = day;
		} if(choice == 2) {
			period = week;
		} if(choice == 3) {
			period = month;
		}
		
		return period;

	}	

	static String StartDay_Period() {

		System.out.print("Enter start day of Period:\t");
		String StartDay_Period= input.next();
		return  StartDay_Period;
	}	

	static String currencyChoice(Map<String, Double>currencyMap) {

		String Currency;
		boolean ok;
		do {

			System.out.println("\nChoose a Currency");
			currencyMap.keySet().forEach(i->System.out.print(i +" "));
			System.out.print("ALL");
			System.out.print("\nEnter the choice:\t");
			Currency = input.next();
			Currency = Currency.toUpperCase();
			
			if(currencyMap.containsKey(Currency) || Currency.equalsIgnoreCase("ALL")) {
				ok = true;
			}
			else {ok= false;}

			if(!ok) {
				System.out.println("\n -- Sorry!!! NO Such Currency");
			}
		}while(!ok);


		return Currency;

	}
	
	static String filePath() {

		String path = "";
		boolean ok;
		do {

			System.out.println("\nProvide path where root folder of Site Report(s) are located");
			System.out.print("\n** IF THEY ARE LOCATED IN PROJECT ROOT FOLDER - Just press [Enter] **\n");
			System.out.print("\nEnter search path (location):\t");
			path = input.next();

			if(path.equalsIgnoreCase("")) {
				ok = true;
			}
			else {
				ok= false;
				}

			if(!ok) {
				System.out.println(" Sorry!!! We only support if site reports are located in project root folder at the moment.");
			}
		}while(!ok);


		return path;

	}
	
	static String StatisticType() {
		String transactionList = "Transactions";
		String profitList = "Profits";
		String statisticType = "";
		
		int choice =0;
		boolean ok;
		do {
			ok = true;

			System.out.format("%nPlease Chose the Statistic Type%n");
			System.out.println("1 - Transaction List ");
			System.out.println("2 - Profits For Currency/ies");

			System.out.print("Enter Your Choice:\t");
			String Period_choice = input.next();
			try {
				choice = Integer.parseInt(Period_choice);
			}catch(NumberFormatException e) {
				System.out.println("Your choice " + Period_choice + " is not accepted!");
				ok = false;
			}

		}while(!ok); 
		
		if(choice == 1) {
			statisticType = transactionList;
		} if(choice == 2) {
			statisticType = profitList;
			} 
		return statisticType;
	}
	/**
	 * Method used for interaction and choices
	 * @param siteChoice a String defining the choice of site
	 * @param periodChoice a String defining the choice of period for stats
	 * @param startDayPeriod a String defining the choice of date starting for stats
	 * @param currencyCode a String defining the choice of currency for stats (ie USD, or ALL)
	 */
	static void printingChoiceForStats(String siteChoice,String periodChoice,String startDayPeriod,String currencyCode) {

		logger.finer("Printing choice for stats Used");
		System.out.println("----------------------------------------------------------- ");

		System.out.println("Choice for statistics: ");
		switch(siteChoice) {

		case "NORTH":
			System.out.println("Site:\tNORTH");
			break;
		case "CENTER":
			System.out.println("Site:\tCENTER");	
			break;
		case "SOUTH":
			System.out.println("Site:\tSOUTH");
			break;
		case "WEST":
			System.out.println("Site:\tWEST");
			break;
		case "ALL":
			System.out.println("Site:\tALL");	
			break;
		}

		switch(periodChoice) {

		case "DAY":
			System.out.println("Period:\t"+"DAY "+startDayPeriod);
			break;
		case "WEEK":
			System.out.println("Period:\t"+"WEEK starting  "+startDayPeriod);
			break;
		case "MONTH":
			System.out.println("Period:\t"+"MONTH starting  "+startDayPeriod);
			break;
		}

		System.out.println("Currency:\t"+currencyCode.toUpperCase());

	}

}
