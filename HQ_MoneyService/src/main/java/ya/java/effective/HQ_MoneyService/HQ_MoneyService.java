
package ya.java.effective.HQ_MoneyService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import affix.java.effective.moneyservice.Transaction;
import affix.java.effective.moneyservice.TransactionMode;

/**
 * @author wael
 *
 */
public class HQ_MoneyService implements HQ{

	@SuppressWarnings("unused")
	private Map<String, List <Transaction>> result;
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

		if(site.equalsIgnoreCase(".ser")) {
			siteName = "ALL";
		}

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

		//	    Open file by part of its name - ie SEARCH
		try {
			list = Files.walk(Paths.get(fileDirectory))
					.map((q) -> q.getFileName().toString())
					.filter((s) -> s.contains(site))
					.collect(Collectors.toList());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Collections.sort(list);

		int startIndex=0;
		for(int i=0;i<list.size();++i){
			if (list.get(i).contains(startDate)) {
				startIndex = i; break;
			}
		}

		if(list.size() < dayCounter) {
			dayCounter = list.size();
		}
		if(list.size() > dayCounter) {
			dayCounter += startIndex;
		}

		calculateStats(date, code, siteName, currencyName, dayCounter, 
				       commissionBuy, comissionSell, list, startIndex);
		
	}


	public static void calculateStats(String date, String code, String siteName, String currencyName, int dayCounter,
			final double commissionBuy, final double comissionSell, List<String> list, int startIndex) {
		
		List<Transaction> t;
		String tempStr;
		
		for(int i=startIndex; i<dayCounter; i++) {
			
			tempStr = list.get(i);

			t = readObject(tempStr);

			int buyAmount = 0;
			int sellAmount = 0;


			if(!code.contains("ALL") ) {


				for(Transaction temp: t) {

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

				for(Transaction temp: t) {

					code = temp.getCurrencyCode();

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

			System.out.println("\nStatistics for site "+siteName+" - Currency " +currencyName+ 
							   " - Date "+date+ " ("+tempStr.replaceAll("[^0-9?!\\-]","")+")" );
			System.out.println("Total  SELL\t"+sellAmount+"\tSEK");
			System.out.println("Total   BUY\t"+buyAmount+"\tSEK");
			System.out.println("Profit    \t"+profit+"\tSEK");
		}
		
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



