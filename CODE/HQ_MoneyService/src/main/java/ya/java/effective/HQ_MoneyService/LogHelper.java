/**
 * 
 */
package ya.java.effective.HQ_MoneyService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 
 * This class is LogHelper for log setup and usage
 * @author Team South
 *
 */
public class LogHelper {


	public static void buildLog () {

		Logger logger;
		FileHandler fh = null;


		// customizing the format for LogRecords using SimpleFormatter
		System.setProperty("java.util.logging.SimpleFormatter.format","%1$tF %1$tT %2$s%n%4$s: %5$s%6$s%n");
		// args date+time(1), origin(2), Logger(3), LogLevel(4), message(5), Exception(6)

		// set up a logger
		logger = Logger.getLogger("ya.java.effective.HQ_MoneyService");

		

		// read configuration details if available
		// default configuration file can be used
		// or set a VM parameter at start up
		// -Djava.util.logging.config.file <Configuration file>

		try {   fh = new FileHandler("HQ_South_SiteLog.txt");
		fh.setFormatter(new SimpleFormatter());

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Send logger output to our FileHandler.
		logger.addHandler(fh);

		// Set level of logging  -  Level.OFF, Level.ALL,
		//  Level.SEVERE, Level.WARNING, Level.INFO, Level.FINE, Level.FINER, Level.FINEST
		Level currentLevel = Level.ALL;

		logger.setLevel(currentLevel); 

		// create and apply a filter for handler
		Filter currentFilter = new HQ_MoneyService_Filter();
		fh.setFilter(currentFilter);

	}
}
