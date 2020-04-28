
package ya.java.effective.HQ_MoneyService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import affix.java.effective.moneyservice.Transaction;
import affix.java.effective.moneyservice.TransactionMode;

/**
 * @author wael
 *
 */
public class HQ_MoneyService implements HQ{

	@SuppressWarnings("unused")
	static Map<String, List <Transaction>> result = new TreeMap<>(); 
	static Map<String, Double> currencyMap = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt").orElse(Collections.emptyMap());

	// Set up a logger
	//	private static Logger logger;

	//	static{
	//		logger = Logger.getLogger("ya.java.effective.moneyservice");
	//	}


	/**
	 * @param result
	 */
	public HQ_MoneyService(Map<String, List<Transaction>> result) {
		super();
		this.result = result;
	}


	@Override
	public Optional<Map<String, List<Transaction>>> filteredTran(Request staticRequest, String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printFilteredMap(String destination) {
		// TODO Auto-generated method stub

	}


	@Override
	public void profitStatistic(String destination) {


	}


	static void searchFilter(String site, String date, String startDate, String code, String fileDirectory) {

		String siteName = site;
		String currencyName = null;
		boolean northIsPresent = false;
		boolean centerIsPresent = false;
		boolean southIsPresent = false;

		int dayCounter = 0;

		final double commissionBuy = 1.005;
		final double comissionSell = 0.995;

		if(date.equalsIgnoreCase("DAY")) {
			dayCounter = 1; 
		}
		if(date.equalsIgnoreCase("WEEK")) {
			dayCounter = 7;
		}
		if(date.equalsIgnoreCase("MONTH")) {
			dayCounter = 30;
		}

		List<String> list = null;
		List<String> listSouth = null;
		List<String> listNorth = null;
		List<String> listCenter = null;

		List<String> fileList = null;

		try {
			fileList = Files.walk(Paths.get(fileDirectory))
					.map((q) -> q.getFileName().toString())
					.collect(Collectors.toList());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Collections.sort(fileList);

		for(String file : fileList) {
			if(file.contains("SOUTH") ) southIsPresent = true;
			if(file.contains("CENTER") ) centerIsPresent = true;
			if(file.contains("NORTH") ) northIsPresent = true;

			//            System.out.println("DEBUG: " +file);
		}

		if(southIsPresent) {
			//	    Open file by part of its name - ie SEARCH
			try {
				listSouth = Files.walk(Paths.get(fileDirectory))
						.map((q) -> q.getFileName().toString())
						.filter((s) -> s.contains("SOUTH"))
						.collect(Collectors.toList());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(centerIsPresent) {
			//	    Open file by part of its name - ie SEARCH
			try {
				listCenter = Files.walk(Paths.get(fileDirectory))
						.map((q) -> q.getFileName().toString())
						.filter((s) -> s.contains("CENTER"))
						.collect(Collectors.toList());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(northIsPresent) {
			//	    Open file by part of its name - ie SEARCH
			try {
				listNorth = Files.walk(Paths.get(fileDirectory))
						.map((q) -> q.getFileName().toString())
						.filter((s) -> s.contains("NORTH"))
						.collect(Collectors.toList());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		
		if(dayCounter == 1) {
			list = Stream.of(listSouth, listCenter, listNorth)
					.flatMap(x -> x.stream())
					.filter((s) -> s.contains(startDate))
					.collect(Collectors.toList());
		}
		if(dayCounter > 1 && siteName != "ALL") {
			list = Stream.of(listSouth, listCenter, listNorth)
					.flatMap(x -> x.stream())
					.filter((s) -> s.contains(site))
					.collect(Collectors.toList());
		}
		if(dayCounter > 1 && site == ".ser") {
			list = Stream.of(listSouth, listCenter, listNorth)
					.flatMap(x -> x.stream())
					.filter((s) -> s.contains(site))
					.collect(Collectors.toList());
		}
		

		Collections.sort(list);
		
		if(list.size() < dayCounter) {
			dayCounter = list.size();
		}

		for(int i=0; i<list.size(); i++) {

			String tempStr = list.get(i);
			String tmpSiteName = "";
			if(tempStr.contains("NORTH")) tmpSiteName = "NORTH";
			if(tempStr.contains("CENTER")) tmpSiteName = "CENTER";
			if(tempStr.contains("SOUTH")) tmpSiteName = "SOUTH";

			List<Transaction> ts = readObject(tempStr);
			result.putIfAbsent(tmpSiteName, ts);
		}


		
		calculateStats(date, code, siteName, currencyName, 
				commissionBuy, comissionSell, list, dayCounter);

	}


	public static void calculateStats(String date, String code, String siteName, String currencyName,
			final double commissionBuy, final double comissionSell, List<String> list, int dayCounter) {


		for(int i=0; i<dayCounter; i++) {
		
		for(String key: result.keySet()){
//			for(Transaction temp: result.get(key)) {
				
				
//				System.out.println("DEBUG: " +temp);
				
				int buyAmount = 0;
				int sellAmount = 0;
				LocalDateTime timeStamp = null; 
				
				if(!code.contains("ALL") ) {

					for(Transaction temp: result.get(key)) {

						timeStamp = temp.getTimeStamp();						

						if(code.equalsIgnoreCase(temp.getCurrencyCode())) {

							currencyName = code;

							if(TransactionMode.BUY.equals(temp.getMode())) {

								int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
								buyAmount += TransactionAmount;

							}
							else if(TransactionMode.SELL.equals(temp.getMode())) {

								int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
								sellAmount += TransactionAmount;

							}	
						}

					}
				}

				if(code.contains("ALL") ) {

					currencyName = "ALL";

					for(Transaction temp: result.get(key)) {

						code = temp.getCurrencyCode();

						timeStamp = temp.getTimeStamp();
						
						if(TransactionMode.BUY.equals(temp.getMode())) {

							int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
							buyAmount += TransactionAmount;

						}
						else if(TransactionMode.SELL.equals(temp.getMode())) {

							int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
							sellAmount += TransactionAmount;

						}	
					}
					code = "ALL";

				}

				int profit = sellAmount - buyAmount;
			
				System.out.println("\nStatistics for site "+key+" - Currency " +currencyName+ 
						" - DateTime "+date+ " ("+(DateTimeFormatter.ISO_DATE) 
													.format(timeStamp)+")" );
				System.out.println("Total  SELL\t"+sellAmount+"\tSEK");
				System.out.println("Total   BUY\t"+buyAmount+"\tSEK");
				System.out.println("Profit    \t"+profit+"\tSEK");
			} // "+date+ " ("+tempStr.replaceAll("[^0-9?!\\-]","")+")
		}
		
//		for(int i=0; i<list.size(); i++) {
//
//			tempStr = list.get(i);
//
//			if(tempStr.contains(north)) siteName = north;
//			if(tempStr.contains(center)) siteName = center;
//			if(tempStr.contains(south)) siteName = south;
//
//			t = readObject(tempStr);
//			result.putIfAbsent(siteName, t);
//			
//			int buyAmount = 0;
//			int sellAmount = 0;
//
//			if(!code.contains("ALL") ) {
//
//				for(Transaction temp: t) {
//
//					if(code.equalsIgnoreCase(temp.getCurrencyCode())) {
//
//						currencyName = code;
//
//						if(TransactionMode.BUY.equals(temp.getMode())) {
//
//							int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
//							buyAmount += TransactionAmount;
//
//						}
//						else if(TransactionMode.SELL.equals(temp.getMode())) {
//
//							int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
//							sellAmount += TransactionAmount;
//
//						}	
//					}
//
//				}
//			}
//
//			if(code.contains("ALL") ) {
//
//				currencyName = "ALL";
//
//				for(Transaction temp: t) {
//
//					code = temp.getCurrencyCode();
//
//					if(TransactionMode.BUY.equals(temp.getMode())) {
//
//						int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
//						buyAmount += TransactionAmount;
//
//					}
//					else if(TransactionMode.SELL.equals(temp.getMode())) {
//
//						int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
//						sellAmount += TransactionAmount;
//
//					}	
//				}
//				code = "ALL";
//
//			}
//
//			int profit = sellAmount - buyAmount;
//
//			System.out.println("\nStatistics for site "+siteName+" - Currency " +currencyName+ 
//					" - Date "+date+ " ("+tempStr.replaceAll("[^0-9?!\\-]","")+")" );
//			System.out.println("Total  SELL\t"+sellAmount+"\tSEK");
//			System.out.println("Total   BUY\t"+buyAmount+"\tSEK");
//			System.out.println("Profit    \t"+profit+"\tSEK");
//		}

//		if(dayCounter == 0) {
//			System.out.println(" Sorry!!! Didn't find anything to present");
//
//		}


	
	
	}


	private static int transactionAmount(String code, double comission, int value) {

		//Get the specific rate

		double rate = currencyMap.get(code).doubleValue();
		//Get the  rate
		double rateIncComission= ((rate * comission));
		int resultCalc = (int) (value * rateIncComission);
		return resultCalc;
	}


	// read data from file using deserialization
	@SuppressWarnings("unchecked")
	public static List<Transaction> readObject(String filename){

		List<Transaction> transactionList = null;

		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
			transactionList = (List<Transaction>)ois.readObject();
			//			System.out.println("\nDEBUG: deserialization reading file - worked!");
		}
		// handle any exception
		catch(IOException | ClassNotFoundException ioe){
			//			logger.warning("Exception occurred during deserialization");
			System.out.println("Exception occurred during deserialization");
		} 

		//		presentFileContents(transactionList);

		return (List<Transaction>) transactionList;
	}

	static void presentFileContents(List<Transaction> transactionList){

		//		System.out.println("\nDEBUG: presenting file content:\n");

		for(int i = 0; i < transactionList.size(); i++) {
			System.out.println(transactionList.get(i));
		}


	}	


}



