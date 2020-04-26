package ya.java.effective.HQ_MoneyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class acts a configurator of the ExchangeApp application
 */
public class ConExApp {

	public static final String Equal_Seperator = "="; 
	public static final String Semicolon_Seperator = ";";
	public static final int Currency_Code_Place = 2;
	public static final int Currency_Rate_Place = 3;

	
	// Set up a logger
				private static Logger logger;
				
				static{
					logger = Logger.getLogger("ya.java.effective.moneyservice");
				}

	
	/**
	 * @param fileName
	 * @return
	 */
	public static Optional<Map<String, Double>> readCurrencyConfig(String fileName) {
		
		logger.finer("Reading "+ fileName +   "config file");
		
		Predicate<String> notValue = (String input) -> { return input.contains(Semicolon_Seperator);};
		Path projectConfigPath = Paths.get(fileName);
		try (Stream<String> currencyRateStream = Files.lines(projectConfigPath).filter(notValue);) {
			return Optional.of(currencyRateStream.collect(Collectors.toMap
					(keyMapper(Semicolon_Seperator, Currency_Code_Place), valueMapper(Semicolon_Seperator, Currency_Rate_Place))));
		}					

		catch (IOException ex) { 
			logger.log(Level.WARNING, "Exception occured ", ex);
		}
		return Optional.empty(); 	

	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return
	 */
	private static Function<String, String> projectConfigParsing(String sepearator, int partNo) {
		Function<String, String> part
		= input -> { 
			String[] parts = input.split(sepearator);
			return parts[partNo].trim();};
		return part;
	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return Function with currency code as a key for the map
	 */
	private static Function<String, String> keyMapper(String sepearator, int partNo) {
		Function<String, String> keyMapper = input -> {
			String[] parts = input.split(sepearator);
			String[] firstPart = parts[partNo].split(" ");
			return firstPart[partNo-1].strip();};
		return keyMapper;
	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return
	 */
	private static Function<String, Double> valueMapper(String sepearator, int partNo) {
		Function<String, Double> valueMapper = input -> {
			String[] parts = input.split(sepearator);
			String[] currency = parts[partNo-1].split(" ");
			double ratePart = Double.parseDouble(parts[partNo].strip());
			double rate = (Integer.parseInt(currency[0].strip()) == 1)? ratePart : ratePart/Integer.parseInt(currency[0].strip());
	
			return rate;
		};
		return valueMapper;}
}


