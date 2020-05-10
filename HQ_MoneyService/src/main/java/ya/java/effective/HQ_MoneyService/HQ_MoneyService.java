
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
 * 
 * This class is the spider in the web, and communicates by crossreference to other classes and methods.
 * @author Team South
 *
 */
public class HQ_MoneyService implements HQ{

	private final Map<String, Map<LocalDate, List <Transaction>>> result; 
	private final Map<LocalDate, Map<String, Double>> currencyMap;

	// Set up a logger
	private static Logger logger;
	private static int  One_day = 1;
	private static int  One_Week = 7;
	private static int  One_Month = 30;


	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}



	/**
	 * Constructor	 
	 * @param result
	 * @param currencyMap a Map of LocalDate of Map of String(Site ie SOUTH) and Double(holding the supported currencies)
	 */
	public HQ_MoneyService(Map<LocalDate,Map<String, Double>> currencyMap) {
		super();
		this.result = new TreeMap<>();
		this.currencyMap = currencyMap; 
	}

	/**
	 * This method is used for Collect all the necessary information, and details   
	 * @param staticrequest holding site, date from, duration, Currency with (All) available as a choice  
	 * @return An optional map with the site name as a key and List of transactions as values.
	 */
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
			logger.fine("Map<String, Map<LocalDate, List <Transaction>>> populated based on the Request from the HQ");
			this.result.putIfAbsent(s.toUpperCase(), temp);
		}

	}

	/**
	 * This method is a keyMaper for building the the filtered Map.
	 * @return Function<String, LocalDate> with the date of this Transaction.
	 */
	private static Function<String, LocalDate> keyMapper() {

		logger.finer("KeyMapper used to populate Map <LocalDate, List<Transaction>>");

		Function<String, LocalDate> keyMapper = input -> {
			String[] parts = input.split("_");
			String[] subParts = parts[2].split("\\.");
			LocalDate keyStringDate = LocalDate.parse(subParts[0]);
			return keyStringDate;
		};
		return keyMapper;
	}

	/**
	 * This method is a valueMaper for building the filtered Map.
	 * @param currencyCode to filter the stream based on Currency code   
	 * @return Function<String, List<Transaction>> list will hold all 
	 * the filtered Transactions by reading the String file name 
	 */
	private static Function<String, List<Transaction>> valueMapper(String currencyCode) {

		logger.finer("Value Mapper used to populate Map <LocalDate, List<Transaction>>");

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

	/**
	 * This method is used to print Transaction statistic by printing the Map of filtered values 
	 */
	@Override
	public void printFilteredMap() {
		this.result.forEach((k, v )-> v.forEach((ke,ve) -> ve.forEach((s) -> System.out.println(k + " : "+ ke + " : "+ s))));

	}


	/**
	 *	This method is used to build a List of ProfitResult object for profit type of statistic for each currency each day.
	 * @param request holding site, date from, duration, Currency with (All) available as a choice  
	 * @return List of ProfitResult object for profit type of statistic for each currency each day.
	 */
	@Override
	public List<ProfitResult> profitStatistic(Request staticRequest) {
		
		logger.finer(" Calculating statistics based on the request from the HQ");
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
						logger.finest(" Calculating Buy amount");
						if (buySellMap.containsKey(Tra.getCurrencyCode())) {
							buySellAmount[1] = buySellMap.get(Tra.getCurrencyCode())[1];
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0] + (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*1.005d);
						}
						else {
							buySellAmount[0] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1+ConExApp.Commission));
						}
					}
					if( Tra.getMode().equals(TransactionMode.SELL)) {
						logger.finest(" Calculating Sell amount");
						if (buySellMap.containsKey(Tra.getCurrencyCode())) {
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0];
							buySellAmount[1] = buySellMap.get(Tra.getCurrencyCode())[1] + (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1-ConExApp.Commission));
						}
						else {
							buySellAmount[1] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*0.995d);

						}				
					}
					buySellMap.put(Tra.getCurrencyCode(), buySellAmount);
					logger.finest("buySellMap [ Map<String, int[]> ] is populated with " + Tra.getCurrencyCode()+",  "
							                                                       + "[ " +buySellAmount[0]+", "+buySellAmount[1]+" ] ");
					                                                                          

					} 
				Iterator<Map.Entry<String, int []>> iterator3 = buySellMap.entrySet().iterator();
				while (iterator3.hasNext()) {
					Map.Entry<String, int []> entry3 = iterator3.next();
					
					logger.finest(" Profit Details from buySellMap added to the class ProfitResult");
					result.add(new ProfitResult(entry3.getValue()[0], entry3.getValue()[1], entry2.getKey(), entry.getKey(), entry3.getKey()));
				}
				buySellMap.clear();
			}
		}
		return result;

	}

	
	/**
	 * Read data from serialized file, as an object
	 * @param filename a String holding a input or output filename
	 * @return transactionList a List holding a Transaction list
	 */
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


	/**
	 * This helping method to change String inputs to int.
	 * @param duration (Day, Week, Month)
	 * @return int based on the string duration.
	 */
	public static int 	dayCounter (String duration) {

		logger.finer("Day Counter used");

		int x = 0 ;
		if(duration.equalsIgnoreCase("DAY")) {
			x = One_day; 
		}
		if(duration.equalsIgnoreCase("WEEK")) {
			x = One_Week;
		}
		if(duration.equalsIgnoreCase("MONTH")) {
			x = One_Month;
		}
		return x;
	}


}



