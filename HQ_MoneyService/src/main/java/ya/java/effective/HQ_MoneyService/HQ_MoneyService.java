
package ya.java.effective.HQ_MoneyService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
			for (LocalDate date = staticRequest.getStartDate(); 
					date.isBefore(staticRequest.getEndDate()); 
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
	 * This method is a keyMaper for building the the filtered Map.
	 * @return Function<String, LocalDate> with the date of this Transaction.
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
	 * This method is a valueMaper for building the filtered Map.
	 * @param currencyCode to filter the stream based on Currency code   
	 * @return Function<String, List<Transaction>> list will hold all 
	 * the filtered Transactions by reading the String file name 
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
	public Set<ProfitResult> profitStatistic() {
		Set<ProfitResult> result = new HashSet<>() ;
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
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0] + 
									(int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1+ConExApp.Commission));
						}
						else {
							buySellAmount[0] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1+ConExApp.Commission));
						}
					}
					if( Tra.getMode().equals(TransactionMode.SELL)) {
						if (buySellMap.containsKey(Tra.getCurrencyCode())) {
							buySellAmount[0] = buySellMap.get(Tra.getCurrencyCode())[0];
							buySellAmount[1] = buySellMap.get(Tra.getCurrencyCode())[1] +
									(int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1-ConExApp.Commission));
						}
						else {
							buySellAmount[1] = (int)(Tra.getAmount()*this.currencyMap.get(entry2.getKey()).get(Tra.getCurrencyCode())*(1-ConExApp.Commission));

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

	@Override
	public void printSummarizeProfitStatistic(ProfitStatisticMode period, Set<ProfitResult> temp, LocalDate endDate, LocalDate startDate) {
		Map<ProfitStatisticMode , TemporalAdjuster> ADJUSTERS = new HashMap<>();

		ADJUSTERS.put(ProfitStatisticMode.DAILY, TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
		ADJUSTERS.put(ProfitStatisticMode.WEEKLY, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
		ADJUSTERS.put(ProfitStatisticMode.MONTHLY, TemporalAdjusters.firstDayOfMonth());
		ADJUSTERS.put(ProfitStatisticMode.ENTIRELY, TemporalAdjusters.ofDateAdjuster(d->d));
		//.minusDays(Period.between(startDate, endDate).getDays())
		Map<LocalDate, List<ProfitResult>> resultDate = temp.stream()
			    .collect(Collectors.groupingBy(item -> item.getTimeStamp()
			            .with(ADJUSTERS.get(period))));
		Iterator<Map.Entry<LocalDate, List<ProfitResult>>> iterator = resultDate.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<LocalDate, List<ProfitResult>> entry = iterator.next();
			Map<String, List<ProfitResult>> resultCode = entry.getValue().stream().collect(Collectors.groupingBy(item -> item.getCurrencyCode()));
			Iterator<Map.Entry<String, List<ProfitResult>>> iterator2 = resultCode.entrySet().iterator();
			while (iterator2.hasNext()) {
				Map.Entry<String, List<ProfitResult>> entry2 = iterator2.next();
				int sell = 0, buy = 0;
				String siteName = "";
				for (ProfitResult x : entry2.getValue() ) {
					sell += x.getSell();
					buy += x.getBuy();
					siteName = x.getSiteName();
				}
				ProfitResult oneResult = new ProfitResult(buy, sell, entry.getKey(),siteName, entry2.getKey());
				System.out.println(oneResult);

			}
		}
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


}



