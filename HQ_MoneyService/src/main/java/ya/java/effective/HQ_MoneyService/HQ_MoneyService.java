
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

	private final Map<String, List <Transaction>> result; 
	private final Map<LocalDate, Map<String, Double>> currencyMap;

	// Set up a logger
	private static Logger logger;



	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}



	/**
	 * Constructor	 
	 * This Constructor will create a new map Map<String, List <Transaction>> inside it in order to fill it with all required details later on
	 * where String is the key and its represent the site name and List of transactions as value  
	 * @param currencyMap a Map of LocalDate of Map of String(Site ie SOUTH) and Double(holding the supported currencies)
	 */
	public HQ_MoneyService(Map<LocalDate,Map<String, Double>> currencyMap) {
		super();
		this.result = new TreeMap<>();
		this.currencyMap = currencyMap; 
	}

	/**
	 * This method is used for Collect all the necessary information, and details based on the user request 
	 * and it will fill Map of result for this class with all details.  
	 * @param staticrequest holding site, date from, duration, Currency with (All) available as a choice,
	 * @param String Files location  
	 * @return An optional map with the site name as a key and List of transactions as values.
	 */
	@Override
	public void filteredTran(Request statisticRequest, String location) {

		logger.finer(" Processing -> filter Transaction");

		for( String s : statisticRequest.getSite()) {
			List<Transaction> tempList = new ArrayList<Transaction>();
			Map <String, List<Transaction>> temp=new TreeMap<>();
			for (LocalDate date = statisticRequest.getStartDate(); 
					date.isBefore(statisticRequest.getEndDate()); 
					date = date.plusDays(1)){
				LocalDate tempDate = date;
				try {
					temp.putAll( Files.walk(Paths.get(location))
							.map(filePath -> filePath.getFileName().toString())
							.filter(fileName-> fileName.contains(tempDate.toString()))
							.filter(fileName-> fileName.contains(s))
							.collect(Collectors.toMap(keyMapper(), valueMapper(statisticRequest.getCurrency()))));
					if (!temp.isEmpty()) {

						for (Transaction x : temp.get(s)) {
							tempList.add(x);
						}
						temp.clear();
					}


				} catch (IOException e1) {
					System.out.println("IOException Occured "+e1);
				}
			}
			logger.fine("Map<String List <Transaction>>> populated based on the Request from the HQ");
			this.result.putIfAbsent(s, tempList);
		}
	}

	/**
	 * This method is a keyMaper for building the the filtered Map.
	 * @return Function<String, LocalDate> with the date of this Transaction.
	 */
	private static Function<String, String> keyMapper() {

		logger.finer("KeyMapper used to populate Map <LocalDate, List<Transaction>>");

		Function<String, String> keyMapper = input -> {
			String[] parts = input.split("_");
			String site = parts[1];
			return site;
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
		this.result.forEach((k, v )->  v.forEach((s)->System.out.println(k + " : "+ s )));

	}


	/**
	 *	This method is used to build a List of ProfitResult object for profit type of statistic.
	 * @param request holding site, date from, duration, Currency with (All) available as a choice  
	 * @return List of ProfitResult object for profit type of statistic based on the request details passed to it.
	 */
	@Override

	public List <ProfitResult> profitStatistic(Request userRequest) {
		List<ProfitResult> finalList = new ArrayList<ProfitResult>();
		Iterator<Map.Entry<String, List<Transaction>>> siteGroup = this.result.entrySet().iterator();
		while (siteGroup.hasNext()) {
			Map.Entry<String, List<Transaction>> transactions = siteGroup.next();
			List<Transaction> temp = transactions.getValue();
			Map<LocalDate, Map<String, Map<TransactionMode, List<Transaction>>>> result = temp.stream()
					.collect(Collectors.groupingBy(item -> item.getTimeStamp().toLocalDate()
							.with(adjuster(userRequest.getPresentingMode())), Collectors.groupingBy(first -> first.getCurrencyCode(), Collectors.groupingBy(third -> third.getMode()))));
			Iterator<Map.Entry<LocalDate, Map<String, Map<TransactionMode, List<Transaction>>>>> dateGroup = result.entrySet().iterator();
			while (dateGroup.hasNext()) {
				Map.Entry<LocalDate, Map<String, Map<TransactionMode, List<Transaction>>>> date = dateGroup.next();
				Iterator<Map.Entry<String, Map<TransactionMode, List<Transaction>>>> currencyGroup = date.getValue().entrySet().iterator();
				while (currencyGroup.hasNext()) {
					Map.Entry<String, Map<TransactionMode, List<Transaction>>> currency = currencyGroup.next();
					Iterator<Map.Entry<TransactionMode, List<Transaction>>> traModeGroup = currency.getValue().entrySet().iterator();
					int sell = 0, buy = 0;
					while (traModeGroup.hasNext()) {
						Map.Entry<TransactionMode, List<Transaction>> tarnansaction = traModeGroup.next();
						for (Transaction x : tarnansaction.getValue()) {
							if (x.getMode().equals(TransactionMode.BUY)){
								buy +=  (int)(x.getAmount()*this.currencyMap.get(date.getKey()).get(x.getCurrencyCode())*(1+ConExApp.Commission));

							}
							if (x.getMode().equals(TransactionMode.SELL)){
								sell += (int)(x.getAmount()*this.currencyMap.get(date.getKey()).get(x.getCurrencyCode())*(1-ConExApp.Commission));
							}
						}
					}

					finalList.add( new ProfitResult(buy, sell, date.getKey(), transactions.getKey(), currency.getKey()));
				}
			}
		}
		return finalList;
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

	private TemporalAdjuster adjuster ( ProfitStatisticMode prsentingModae) {
		Map<ProfitStatisticMode , TemporalAdjuster> ADJUSTERS = new HashMap<>();

		ADJUSTERS.put(ProfitStatisticMode.DAILY, TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
		ADJUSTERS.put(ProfitStatisticMode.WEEKLY, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
		ADJUSTERS.put(ProfitStatisticMode.MONTHLY, TemporalAdjusters.firstDayOfMonth());

		return ADJUSTERS.get(prsentingModae);
	}



}



