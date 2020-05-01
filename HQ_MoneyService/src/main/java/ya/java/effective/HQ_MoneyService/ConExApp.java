package ya.java.effective.HQ_MoneyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class acts a configurator of the HQ App application
 */
public class ConExApp {

	/**
	 * Equal Separator
	 */
	public static final String Equal_Separator = "="; 
	/**
	 * Semicolon Separator
	 */
	public static final String Semicolon_Separator = ";";
	/**
	 * Currency Code Place in the CurrencyConfig File
	 */
	public static final int Currency_Code_Place = 2;
	/**
	 * Currency Rate Place in the CurrencyConfig File
	 */
	public static final int Currency_Rate_Place = 3;



	// Set up a logger
	private static Logger logger;

	static{
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");
	}

	/**
	 * This method is used for configure the Sites Names
	 * @param fileName will call the config file by its name
	 * @return Optional List of sites names as list of String
	 */
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
	 * This method read all in specific directory Currency Config files.
	 * @param duration A String Value for specific how much is the interval to read files 
	 * @param startDate The beginning of the duration interval for reading files. 
	 * @param filesLocations The directory of files location 
	 * @return Optional Map Holding Currency Config for long period ( Localdata the date of the file as a key 
	 * and Map as value of Currency Code String as a key and Double as a value of each currency).
	 */
	public static Optional<Map<LocalDate, Map<String, Double>>> readCurrencyConfigFiles(String duration, LocalDate startDate,String filesLocations) {

		logger.finer("Creating <Map<LocalDate, Map<String, Double>>> from  Currency Config files");

		Map<LocalDate, Map<String, Double>> maintemp = new TreeMap<>();	
		List<String> temp = new ArrayList<String>();

		for (LocalDate date = startDate; 
				date.isBefore(startDate.plusDays(HQ_MoneyService.dayCounter(duration))); 
				date = date.plusDays(1)){
			LocalDate tempDate = date;
			try {
				temp = ( Files.walk(Paths.get(filesLocations))
						.map(filePath -> filePath.getFileName().toString())
						.filter(fileName-> fileName.contains(tempDate.toString()))
						.filter(fileName-> fileName.contains("CurrencyConfig_"))
						.collect(Collectors.toList()));

			} catch (IOException e1) {
				System.out.println("An IOException occurred " + e1);

			}
			for (String x : temp) {
				maintemp.putIfAbsent(tempDate, readCurrencyConfig(x));
			}
		}
		return Optional.of(maintemp);

	}




	/**
	 * This method read the Currency Config file and sets application constants
	 * @param fileName is the name of the currencyConfig file
	 * @return Map Holding Currency Config( Currency Code as Key and Double Value).
	 */
	static Map<String, Double> readCurrencyConfig(String fileName) {
		Predicate<String> notValue = (String input) -> { return input.contains(Semicolon_Separator);};
			logger.finer("Reading "+ fileName +   "config file");

			Path projectConfigPath = Paths.get(fileName);
			try (Stream<String> currencyRateStream = Files.lines(projectConfigPath).filter(notValue);) {
				return currencyRateStream.collect(Collectors.toMap
						(keyMapper(Semicolon_Separator, Currency_Code_Place), valueMapper(Semicolon_Separator, Currency_Rate_Place)));
			}					

			catch (IOException ex) { 
				System.out.println("An IOException occurred for file " + fileName);
			}
			return Collections.emptyMap(); 
		}

	



/**
 * This method is helping method for parsing The Project Config File 
 * @param separator for pars the line based on it.  
 * @param partNo Which index number should get it back;
 * @return Function<String, String> with the specific part for use in Stream
 */
public static Function<String, String> helpToParsing(String sepearator, int partNo) {

	logger.finer("Project config Parsing used");
	Function<String, String> part
	= input -> { 
		String[] parts = input.split(sepearator);
		return parts[partNo].trim();};
		return part;
}

/**
 * This method is a keyMaper for building the Currency Map
 * @param separator for pars the line based on it.  
 * @param partNo Which index number should get it back;
 * @return Function<String, String> with the specific part as String for use in Stream
 */
private static Function<String, String> keyMapper(String sepearator, int partNo) {

	Function<String, String> keyMapper = input -> {
		String[] parts = input.split(sepearator);
		String[] firstPart = parts[partNo].split(" ");
		return firstPart[partNo-1].strip();};
		return keyMapper;
}

/**
 * This method is a valueMaper for building the Currency Map
 * @param separator for pars the line based on it.  
 * @param partNo Which index number should get it back;
 * @return Function<String, Currency> with the a Currency Object for use in Stream
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


