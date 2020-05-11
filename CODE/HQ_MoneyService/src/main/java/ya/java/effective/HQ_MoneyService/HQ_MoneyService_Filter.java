package ya.java.effective.HQ_MoneyService;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 
 * This class is the HQ_MoneyService_Filter that implements Filter, for purpose of logging
 * @author Team South
 *
 */
public class HQ_MoneyService_Filter implements Filter {

	@Override
	public boolean isLoggable(LogRecord record) {

		if(record.getLevel().equals(Level.FINE) || record.getLevel().equals(Level.FINER)
				|| record.getLevel().equals(Level.FINEST)){
			return true;
		}
		else{	
			System.out.println("LogRecord dropped by MoneyService_Filter: "+record.getSourceClassName());
			return false;
		}

	}

}


