package ya.java.effective.HQ_MoneyService;

import java.util.List;
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

			if(choice == 1) {
				site = north;
			}else if(choice == 2) {
				site = center;
			}else if(choice == 3) {
				site = south;
			}else if(choice == 4) {
				site = west;
			}else if(choice == 5) {
				site = "ALL";
			}else {
				ok = false;
				System.out.println("Wrong Choice!");
			}
		}while(!ok); 



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

			if(choice == 1) {
				period = day;
			}else if(choice == 2) {
				period = week;
			}else if(choice == 3) {
				period = month;
			}else {
				ok = false;
				System.out.println("Wrong Choice!");
			}
		}while(!ok); 


		return period;

	}	

	static String StartDay_Period() {
		System.out.print("");
		System.out.print("Enter start day of Period:\t");
		String StartDay_Period= input.next();
		return  StartDay_Period;
	}	

	static String currencyChoice(List<String> availableCurrency) {

		String Currency;
		boolean ok;
		do {

			System.out.println("\nChoose a Currency");
			availableCurrency.forEach(i->System.out.print(i +" "));
			System.out.print("ALL");
			System.out.print("\nEnter the choice:\t");
			Currency = input.next().toUpperCase();

			if(availableCurrency.contains(Currency) || Currency.equalsIgnoreCase("ALL")) {
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

			System.out.format("%nPlease Choose Statistic Type%n");
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

			if(choice == 1) {
				statisticType = transactionList;
			}else if(choice == 2) {
				statisticType = profitList;
			}else {
				ok = false;
				System.out.println("Wrong Choice!");
			}
		}while(!ok); 

		return statisticType;
	}

}
