
package ya.java.effective.HQ_MoneyService;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import affix.java.effective.moneyservice.Transaction;

/**
 * @author wael
 *
 */
public class HQ_MoneyService implements HQ{

	@SuppressWarnings("unused")
	private Map<String, List <Transaction>> result;

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
		// TODO Auto-generated method stub

	}




}



