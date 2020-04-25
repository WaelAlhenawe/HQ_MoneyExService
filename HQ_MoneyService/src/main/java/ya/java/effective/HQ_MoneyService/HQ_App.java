package ya.java.effective.HQ_MoneyService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import affix.java.effective.moneyservice.Transaction;
import affix.java.effective.moneyservice.TransactionMode;

/**
 * @author wael + robin & batman and mr Boba & SuperMan
 *
 */
public class HQ_App 
{
	static Map<String, Double> currencyMap = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt").orElse(Collections.emptyMap());
	//	private static Logger logger;

	public static void main( String[] args )
	{
		System.out.println( "Stats\n---------------------\n" );

		List<Transaction> t = new ArrayList<Transaction>();

		//		Predicate<Transaction> shortStringPredicate
		//		= (Transaction input) -> { return (input.getCurrencyCode().equals("EUR") );};
		//		Predicate<Transaction> shortStringPredicate2
		//		= (Transaction input) -> { return (input.getMode().equals(TransactionMode.SELL) );};

		//		IntSummaryStatistics stats = t.stream().filter(shortStringPredicate.and(shortStringPredicate2)).collect(
		//				Collectors.summarizingInt(Transaction::getAmount));

		final double commissionBuy = 1.005;
		final double comissionSell = 0.995;

		String fileDirectory = "";
		String searchString = "SOUTH";

		List<String> list = null;

		//	    Open file by part of its name - ie SEARCH
		try {
			list = Files.walk(Paths.get(fileDirectory))
					.map((q) -> q.getFileName().toString())
					.filter((s) -> s.contains(searchString))
					.collect(Collectors.toList());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(list.size() == 1){
			System.out.println("Your file is:");
			System.out.println("DEBUG: "+list);
			t = readObject(list.get(0));
		} else {
			System.out.println("There are more than one file");
			list.forEach(i->System.out.println("DEBUG: "+i));
			for(String tempStr: list) {

				t = readObject(tempStr);

				String code = "GBP";
				//		TransactionMode mode = TransactionMode.BUY;
				//		TransactionMode mode = TransactionMode.SELL;
				int buyAmount = 0;
				int sellAmount = 0;
				String currCode = null;

				for(Transaction temp: t) {

					if(code.equalsIgnoreCase(temp.getCurrencyCode())) {

						currCode = temp.getCurrencyCode();

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
				int profit = sellAmount - buyAmount;

				System.out.println("\nStatistics for site "+searchString+" - Currency " +currCode );
				System.out.println("Total  SELL\t"+sellAmount+"  SEK");
				System.out.println("Total   BUY\t"+buyAmount+"  SEK");
				System.out.println("Profit    \t"+profit+"  SEK");
			}
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
			System.out.println("\nDEBUG: deserialization reading file - worked!");
		}
		// handle any exception
		catch(IOException | ClassNotFoundException ioe){
			//			logger.warning("Exception occurred during deserialization");
			System.out.println("Exception occurred during deserialization");
		} 

		presentFileContents(transactionList);

		return (List<Transaction>) transactionList;
	}

	static void presentFileContents(List<Transaction> transactionList){

		System.out.println("\nDEBUG: presenting file content:\n");

		for(int i = 0; i < transactionList.size(); i++) {
			System.out.println(transactionList.get(i));
		}


	}	


}
