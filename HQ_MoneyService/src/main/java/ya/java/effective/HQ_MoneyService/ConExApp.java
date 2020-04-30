package ya.java.effective.HQ_MoneyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
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
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}

	public static Optional<List<String>> readSiteNamesConfig(String fileName) {

		logger.finer("Reading "+ fileName +   "config file");


		Path projectConfigPath = Paths.get(fileName);
		try (Stream<String> currencyRateStream = Files.lines(projectConfigPath);) {
			return Optional.of(currencyRateStream.collect(Collectors.toList()));
		}					

		catch (IOException ex) { 
			System.out.println("An IOException occurred for file " + fileName);
		}
		return Optional.empty(); 	

	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Optional<Map<LocalDate, Map<String, Double>>> readCurrencyConfigFiles(String duration, LocalDate startDate,String filesLocations) {

		logger.finer("Creating <Map<LocalDate, Map<String, Double>>> from  Currency Config files");
		
		Map<LocalDate, Map<String, Double>> maintemp = new TreeMap<>();	


		for (LocalDate date = startDate; 
				date.isBefore(startDate.plusDays(HQ_MoneyService.dayCounter(duration))); 
				date = date.plusDays(1)){
			LocalDate tempDate = date;
			try {
				maintemp.putAll( Files.walk(Paths.get(filesLocations))
						.map(filePath -> filePath.getFileName().toString())
						.filter(fileName-> fileName.contains(tempDate.toString()))
						.filter(fileName-> fileName.contains("CurrencyConfig_"))
						.collect(Collectors.toMap(k->tempDate, v-> readCurrencyConfig(v.toString()).get())));

			} catch (IOException e1) {
				System.out.println("An IOException occurred " + e1);
				
			}
			//			filesNames.forEach(q-> readCurrencyConfig(q));
			//			maintemp.putIfAbsent(date, temp);
		}
		return Optional.of(maintemp);
	}


	//		Path projectConfigPath = Paths.get(fileName);
	//		try (Stream<String> currencyRateStream = Files.lines(projectConfigPath).filter(notValue);) {
	//			return Optional.of(currencyRateStream.collect(Collectors.toMap
	//					(keyMapper(Semicolon_Seperator, Currency_Code_Place), valueMapper(Semicolon_Seperator, Currency_Rate_Place))));
	//		}					
	//
	//	catch (IOException ex) { 
	//		logger.log(Level.WARNING, "Exception occured ", ex);
	//	}
	//	return Optional.empty(); 	
	//
	//}

	/**
	 * @param fileName
	 * @return
	 */
	public static Optional<Map<String, Double>> readCurrencyConfig(String fileName) {

		logger.finer("Reading "+ fileName +   "config file");

		Predicate<String> notValue = (String input) -> { return input.contains(Semicolon_Seperator);};
		Path projectConfigPath = Paths.get(fileName);
		try (Stream<String> currencyRateStream = Files.lines(projectConfigPath).filter(notValue);) {
			return Optional.ofNullable(currencyRateStream.collect(Collectors.toMap
					(keyMapper(Semicolon_Seperator, Currency_Code_Place), valueMapper(Semicolon_Seperator, Currency_Rate_Place))));
		}					

		catch (IOException ex) { 
			System.out.println("An IOException occurred for file " + fileName);
		}
		return Optional.empty(); 	

	}

	/**
	 * @param sepearator
	 * @param partNo
	 * @return
	 */
	public static Function<String, String> projectConfigParsing(String sepearator, int partNo) {
		
		logger.finer("Project config Parsing used");
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


