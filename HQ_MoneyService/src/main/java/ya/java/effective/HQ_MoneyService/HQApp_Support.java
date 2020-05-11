package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * This is a support class for user interaction using CLI
 * @author Team South
 *
 */
/**
 * @author wael
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

		logger.finer(" Get SiteChoice from the User");
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


	static LocalDate PeriodChoice(LocalDate startDate) {


		LocalDate period = null;
		boolean ok;
		do {
			ok = true;

			System.out.println("\nChoose a Period ");
			System.out.println("1 - DAY ");
			System.out.println("2 - WEEK");
			System.out.println("3 - MONTH");
			System.out.println("4 - Specific Date");
			System.out.println("");

			System.out.println("0 - Exit");

			System.out.print("Enter Your Choice:\t");
			String Period_choice = input.next();

			if(Period_choice.equals("1")) {
				period = startDate.plusDays(1);
			}else if(Period_choice.equals("2")) {
				period = startDate.plusWeeks(1);
			}else if(Period_choice.equals("3")) {
				period = startDate.plusMonths(1);
			}else if(Period_choice.equals("4")) {
				Boolean flag = false;  
				do {
					System.out.print("Please Enter the Date (yyyy-mm-dd) OR (0) to Go Back:\t");
					String endDate = input.next();
					if ( endDate.equals("0")) {
						flag = true;
						ok = false;
					}else {
						try {
							period = LocalDate.parse(endDate);
							period.plusDays(1);
							flag = true;
						} catch (Exception e) {
							logger.fine("Worng date input" + endDate + e);
							flag = false;
							ok = false;
							System.out.format("Wrong Input!%n");
						} 
					}
				} while (!flag);
			}else if(Period_choice.equals("0")) {
				System.exit(0);
			}else {
				ok = false;
				logger.fine("Wrong Period Choice" + Period_choice);
				System.out.println("Wrong Choice!");
			}
		}while(!ok); 
		return period;
	}	

	static ProfitStatisticMode profitStatisticMode() {

		boolean ok;
		do {
			ok = true;

			System.out.println("\nPlease Choose a Period Statistic Summarizes Mode");
			System.out.println("1 - Daily");
			System.out.println("2 - Weekly");
			System.out.println("3 - Monthly");
			System.out.println("4 - The whole Period");
			System.out.println("");

			System.out.println("0 - Exit");
			System.out.print("Enter Your Choice:\t");

			String Period_choice = input.next();

			if(Period_choice.equals("1")) {
				return ProfitStatisticMode.DAILY;
			}else if(Period_choice.equals("2")) {
				return ProfitStatisticMode.WEEKLY;
			}else if(Period_choice.equals("3")) {
				return ProfitStatisticMode.MONTHLY;
			}else if(Period_choice.equals("4")) {
				return ProfitStatisticMode.ENTIRELY;
			}else if(Period_choice.equals("0")) {
				System.exit(0);
			
			}else {
				ok = false;
				logger.fine("Wrong Period Choice" + Period_choice);
				System.out.println("Wrong Choice!");
			}
		}while(!ok); 


		return null;

	}	


	static LocalDate StartDay_Period() {
		String temp;
		LocalDate startDay = null;
		Boolean flag = false;
		do {
			System.out.print("Enter start day of the Statistic OR (0) To Exit:\t");
			temp = input.next();
			if ( temp.equals("0")) {
				System.exit(0);
			}else {
			try {
				startDay = LocalDate.parse(temp);
				flag = true;

			} catch (Exception e) {
				logger.fine("Worng date input" + temp + e);
				flag = false;
				System.out.format("Wrong Input!%n");
			} 
			}
		} while (!flag);
		return  startDay;

	}	

	static String currencyChoice(List<String> availableCurrency) {

		logger.finer(" Get the currency choice from the User");
		
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
		logger.finer("Get the Statistics Type [ Transactions List / Profits ] for the specific Day/Period from the User ");
		String transactionList = "Transactions";
		String profitList = "Profits";
		String statisticType = "";

		int choice =0;
		boolean ok;
		do {
			ok = true;

			System.out.format("%nPlease Choose Statistic Type%n");
			System.out.println("1 - Transaction List ");
			System.out.println("2 - Profit For Currency/ies");

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
