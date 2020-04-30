
package ya.java.effective.HQ_MoneyService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import affix.java.effective.moneyservice.Transaction;
import affix.java.effective.moneyservice.TransactionMode;

/**
 * @author wael
 *
 */
public class HQ_MoneyService implements HQ{

	private final Map<String, Map<LocalDate, List <Transaction>>> result; 
	private final Map<LocalDate, Map<String, Double>> currencyMap;

	// Set up a logger
	private static Logger logger;

	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}


	/**
	 * @param result
	 */
	public HQ_MoneyService(Map<LocalDate,Map<String, Double>> currencyMap) {
		super();
		this.result = new TreeMap<>();
		this.currencyMap = currencyMap; 
	}



	@Override
	public void filteredTran(Request staticRequest, String location) {

		logger.finer(" Processing -> filter Transaction");

		Map <LocalDate, List<Transaction>> temp = new TreeMap<>();
		for( String s : staticRequest.getSite()) {
			for (LocalDate date = staticRequest.getDate(); 
					date.isBefore(staticRequest.getDate().plusDays(dayCounter(staticRequest.getDuration()))); 
					date = date.plusDays(1)){
				LocalDate tempDate = date;
				try {
					temp.putAll( Files.walk(Paths.get(location))
							.map(filePath -> filePath.getFileName().toString())
							.filter(fileName-> fileName.contains(tempDate.toString()))
							.filter(fileName-> fileName.contains(s))
							.collect(Collectors.toMap(keyMapper(), valueMapper(staticRequest.getCurrency()))));

				} catch (IOException e1) {
					System.out.println("IOException Occured "+e1);
				}
			}
			this.result.putIfAbsent(s.toUpperCase(), temp);
		}

	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return Function with currency code as a key for the map
	 */
	private static Function<String, LocalDate> keyMapper() {

		logger.finer("KeyMapper used");

		Function<String, LocalDate> keyMapper = input -> {
			String[] parts = input.split("_");
			String[] subParts = parts[2].split("\\.");
			LocalDate keyStringDate = LocalDate.parse(subParts[0]);
			return keyStringDate;
		};
		return keyMapper;
	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return
	 */
	private static Function<String, List<Transaction>> valueMapper(String currencyCode) {

		logger.finer("Value Mapper used");

		Function<String, List<Transaction>> valueMapper = input -> {
			List<Transaction>temp = Collections.emptyList();
			if (currencyCode.equals("ALL") ) {
				temp = readObject(input);
			}else if (!currencyCode.equals("ALL")){
				temp = readObject(input)
						.stream()
						.filter(s->s.getCurrencyCode().equals(currencyCode))
						.collect(Collectors.toList());
			}
			return temp;
		};
		return valueMapper;}

	@Override
	public void printFilteredMap(String destination) {
		this.result.forEach((k, v )-> v.forEach((ke,ve) -> ve.forEach((s) -> System.out.println(k + " : "+ ke + " : "+ s))));

	}


	@Override
	public List<ProfitResult> profitStatistic(Request staticRequest) {
		List<ProfitResult> result = new ArrayList<>();
		Map<String, int[]> buySellMap = new TreeMap<String, int[]>();
		Iterator<Map.Entry<String, Map<LocalDate, List <Transaction>>>> iterator = this.result.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Map<LocalDate, List <Transaction>>> entry = iterator.next();
			Iterator<Map.Entry<LocalDate, List <Transaction>>> iterator2 = entry.getValue().entrySet().iterator();
			while (iterator2.hasNext()) {
				Map.Entry<LocalDate, List <Transaction>> entry2 = iterator2.next();
				for (Transaction Tra : entry2.getValue()) {
					int buySellAmount [] = new int [] {0,0};
					if( Tra.getMode().equals(TransactionMode.BUY)) {
						if (buySellMap.containsKey(Tra.getCurrencyCode())) {
							buySellAmount[1] = buySellMap.get(Tra.getCurrencyCode())[1];
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0] + (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*1.005d);
						}
						else {
							buySellAmount[0] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*1.005d);
						}
					}
					if( Tra.getMode().equals(TransactionMode.SELL)) {
						if (buySellMap.containsKey(Tra.getCurrencyCode())) {
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0];
							buySellAmount[1] = buySellMap.get(Tra.getCurrencyCode())[1] + (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*0.995d);
						}
						else {
							buySellAmount[1] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*0.995d);

						}				
					}
					buySellMap.put(Tra.getCurrencyCode(), buySellAmount);

					}
				Iterator<Map.Entry<String, int []>> iterator3 = buySellMap.entrySet().iterator();
				while (iterator3.hasNext()) {
					Map.Entry<String, int []> entry3 = iterator3.next();
					
					
					result.add(new ProfitResult(entry3.getValue()[0], entry3.getValue()[1], entry2.getKey(), entry.getKey(), entry3.getKey()));
				}
				buySellMap.clear();
			}
		}
		return result;
	}





	//	static void searchFilter(String site, Duration date, LocalDate startDate, String code, String fileDirectory) {
	//
	//		String siteName = site;
	//		String currencyName = null;
	//		boolean northIsPresent = false;
	//		boolean centerIsPresent = false;
	//		boolean southIsPresent = false;
	//
	//		int dayCounter = 0;
	//
	//		final double commissionBuy = 1.005;
	//		final double comissionSell = 0.995;
	//
	//		if(date.equalsIgnoreCase("DAY")) {
	//			dayCounter = 1; 
	//		}
	//		if(date.equalsIgnoreCase("WEEK")) {
	//			dayCounter = 7;
	//		}
	//		if(date.equalsIgnoreCase("MONTH")) {
	//			dayCounter = 30;
	//		}
	//
	//		List<String> list = null;
	//		List<String> listSouth = null;
	//		List<String> listNorth = null;
	//		List<String> listCenter = null;
	//
	//		List<String> fileList = null;
	//
	//		try {
	//			fileList = Files.walk(Paths.get(fileDirectory))
	//					.map((q) -> q.getFileName().toString())
	//					.filter(q-> q.contains(startDate.range(startDate.adjustInto(temporal)).toString()))
	//					.collect(Collectors.toList());
	//
	//		} catch (IOException e1) {
	//			// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		}

	//		Collections.sort(fileList);
	//
	//		for(String file : fileList) {
	//			if(file.contains("SOUTH") ) southIsPresent = true;
	//			if(file.contains("CENTER") ) centerIsPresent = true;
	//			if(file.contains("NORTH") ) northIsPresent = true;
	//
	//			//            System.out.println("DEBUG: " +file);
	//		}
	//
	//		if(southIsPresent) {
	//			//	    Open file by part of its name - ie SEARCH
	//			try {
	//				listSouth = Files.walk(Paths.get(fileDirectory))
	//						.map((q) -> q.getFileName().toString())
	//						.filter((s) -> s.contains("SOUTH"))
	//						.collect(Collectors.toList());
	//
	//			} catch (IOException e1) {
	//				// TODO Auto-generated catch block
	//				e1.printStackTrace();
	//			}
	//		}
	//		if(centerIsPresent) {
	//			//	    Open file by part of its name - ie SEARCH
	//			try {
	//				listCenter = Files.walk(Paths.get(fileDirectory))
	//						.map((q) -> q.getFileName().toString())
	//						.filter((s) -> s.contains("CENTER"))
	//						.collect(Collectors.toList());
	//
	//			} catch (IOException e1) {
	//				// TODO Auto-generated catch block
	//				e1.printStackTrace();
	//			}
	//		}
	//		if(northIsPresent) {
	//			//	    Open file by part of its name - ie SEARCH
	//			try {
	//				listNorth = Files.walk(Paths.get(fileDirectory))
	//						.map((q) -> q.getFileName().toString())
	//						.filter((s) -> s.contains("NORTH"))
	//						.collect(Collectors.toList());
	//
	//			} catch (IOException e1) {
	//				// TODO Auto-generated catch block
	//				e1.printStackTrace();
	//			}
	//
	//		}
	//		
	//		
	//		if(dayCounter == 1) {
	//			list = Stream.of(listSouth, listCenter, listNorth)
	//					.flatMap(x -> x.stream())
	//					.filter((s) -> s.contains(startDate))
	//					.collect(Collectors.toList());
	//		}
	//		if(dayCounter > 1 && site != ".ser") {
	//			list = Stream.of(listSouth, listCenter, listNorth)
	//					.flatMap(x -> x.stream())
	//					.filter((s) -> s.contains(site))
	//					.collect(Collectors.toList());
	//		}
	//		if(dayCounter > 1 && site == ".ser") {
	//			list = Stream.of(listSouth, listCenter, listNorth)
	//					.flatMap(x -> x.stream())
	//					.filter((s) -> s.contains(site))
	//					.collect(Collectors.toList());
	//		}
	//		
	//
	//		Collections.sort(list);
	//		
	//		if(list.size() < dayCounter) {
	//			dayCounter = list.size();
	//		}
	//
	//		for(int i=0; i<list.size(); i++) {
	//
	//			String tempStr = list.get(i);
	//			String tmpSiteName = "";
	//			if(tempStr.contains("NORTH")) tmpSiteName = "NORTH";
	//			if(tempStr.contains("CENTER")) tmpSiteName = "CENTER";
	//			if(tempStr.contains("SOUTH")) tmpSiteName = "SOUTH";
	//
	//			List<Transaction> ts = readObject(tempStr);
	//			result.putIfAbsent(tmpSiteName, ts);
	//		}
	//
	//
	//		
	//		calculateStats(date, code, siteName, currencyName, 
	//				commissionBuy, comissionSell, list, dayCounter);
	//
	//	}
	//
	//
	//	public static void calculateStats(String date, String code, String siteName, String currencyName,
	//			final double commissionBuy, final double comissionSell, List<String> list, int dayCounter) {
	//
	//
	////		for(int i=0; i<dayCounter; i++) {
	//		
	//		for(String key: result.keySet()){
	////			for(Transaction temp: result.get(key)) {
	//				
	//				
	////				System.out.println("DEBUG: " +temp);
	//				
	//				int buyAmount = 0;
	//				int sellAmount = 0;
	//				LocalDateTime timeStamp = null; 
	//				
	//				if(!code.contains("ALL") ) {
	//
	//					for(Transaction temp: result.get(key)) {
	//
	//						timeStamp = temp.getTimeStamp();						
	//
	//						if(code.equalsIgnoreCase(temp.getCurrencyCode())) {
	//
	//							currencyName = code;
	//
	//
	//							if(TransactionMode.BUY.equals(temp.getMode())) {
	//
	//								int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
	//								buyAmount += TransactionAmount;
	//
	//							}
	//							else if(TransactionMode.SELL.equals(temp.getMode())) {
	//
	//								int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
	//								sellAmount += TransactionAmount;
	//
	//							}	
	//						}
	//
	//					}
	//				}
	//
	//
	//				if(code.contains("ALL") ) {
	//
	//					currencyName = "ALL";
	//
	//					for(Transaction temp: result.get(key)) {
	//
	//						code = temp.getCurrencyCode();
	//
	//						timeStamp = temp.getTimeStamp();
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
	//
	//						}	
	//					}
	//					code = "ALL";
	//
	//
	//				}
	//
	//				int profit = sellAmount - buyAmount;
	//			
	//				System.out.println("\nStatistics for site "+key+" - Currency " +currencyName+ 
	//						" - Date "+date+ " ("+(DateTimeFormatter.ISO_DATE) 
	//													.format(timeStamp)+")" );
	//				System.out.println("Total  SELL\t"+sellAmount+"\tSEK");
	//				System.out.println("Total   BUY\t"+buyAmount+"\tSEK");
	//				System.out.println("Profit    \t"+profit+"\tSEK");
	//			} // "+date+ " ("+tempStr.replaceAll("[^0-9?!\\-]","")+")
	////		}
	//		
	////		for(int i=0; i<list.size(); i++) {
	////
	////			tempStr = list.get(i);
	////
	////			if(tempStr.contains(north)) siteName = north;
	////			if(tempStr.contains(center)) siteName = center;
	////			if(tempStr.contains(south)) siteName = south;
	////
	////			t = readObject(tempStr);
	////			result.putIfAbsent(siteName, t);
	////			
	////			int buyAmount = 0;
	////			int sellAmount = 0;
	////
	////			if(!code.contains("ALL") ) {
	////
	////				for(Transaction temp: t) {
	////
	////					if(code.equalsIgnoreCase(temp.getCurrencyCode())) {
	////
	////						currencyName = code;
	////
	////						if(TransactionMode.BUY.equals(temp.getMode())) {
	////
	////							int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
	////							buyAmount += TransactionAmount;
	////
	////						}
	////						else if(TransactionMode.SELL.equals(temp.getMode())) {
	////
	////							int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
	////							sellAmount += TransactionAmount;
	////
	////						}	
	////					}
	////
	////				}
	////			}
	////
	////			if(code.contains("ALL") ) {
	////
	////				currencyName = "ALL";
	////
	////				for(Transaction temp: t) {
	////
	////					code = temp.getCurrencyCode();
	////
	////					if(TransactionMode.BUY.equals(temp.getMode())) {
	////
	////						int TransactionAmount =  transactionAmount(code,commissionBuy,temp.getAmount())  ;
	////						buyAmount += TransactionAmount;
	////
	////					}
	////					else if(TransactionMode.SELL.equals(temp.getMode())) {
	////
	////						int TransactionAmount =  transactionAmount(code,comissionSell,temp.getAmount())  ;
	////						sellAmount += TransactionAmount;
	////
	////					}	
	////				}
	////				code = "ALL";
	////
	////			}
	////
	////			int profit = sellAmount - buyAmount;
	////
	////			System.out.println("\nStatistics for site "+siteName+" - Currency " +currencyName+ 
	////					" - Date "+date+ " ("+tempStr.replaceAll("[^0-9?!\\-]","")+")" );
	////			System.out.println("Total  SELL\t"+sellAmount+"\tSEK");
	////			System.out.println("Total   BUY\t"+buyAmount+"\tSEK");
	////			System.out.println("Profit    \t"+profit+"\tSEK");
	////		}
	//
	////		if(dayCounter == 0) {
	////			System.out.println(" Sorry!!! Didn't find anything to present");
	////
	////		}
	//
	//
	//	
	//	
	//	}
	//
	//
	//	private static int transactionAmount(String code, double comission, int value) {
	//
	//		//Get the specific rate
	//
	//		double rate = currencyMap.get(code);
	//		//Get the  rate
	//		double rateIncComission= (rate * comission);
	//		int resultCalc = (int) (value * rateIncComission);
	//		return resultCalc;
	//	}
	//
	// read data from file using deserialization
	@SuppressWarnings("unchecked")
	public static List<Transaction> readObject(String filename){

		logger.finer(" Reading object (Deserializtion) used");
		List<Transaction> transactionList = null;

		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
			transactionList = (List<Transaction>)ois.readObject();
		}
		catch(IOException | ClassNotFoundException ioe){

			System.out.println("Exception occurred during deserialization");
		} 

		//		presentFileContents(transactionList);

		return (List<Transaction>) transactionList;
	}

	static void presentFileContents(List<Transaction> transactionList){

		logger.finer("Presenting File contents used");	
		//		System.out.println("\nDEBUG: presenting file content:\n");

		for(int i = 0; i < transactionList.size(); i++) {
			System.out.println(transactionList.get(i));
		}
	}


	public static int 	dayCounter (String duration) {

		logger.finer("Day Counter used");

		int x = 0 ;
		if(duration.equalsIgnoreCase("DAY")) {
			x = 1; 
		}
		if(duration.equalsIgnoreCase("WEEK")) {
			x = 7;
		}
		if(duration.equalsIgnoreCase("MONTH")) {
			x = 30;
		}
		return x;
	}


}



