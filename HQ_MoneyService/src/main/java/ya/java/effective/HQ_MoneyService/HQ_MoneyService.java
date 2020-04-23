
package ya.java.effective.HQ_MoneyService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import affix.java.effective.moneyservice.Transaction;

/**
 * @author wael
 *
 */
public class HQ_MoneyService implements HQ{

		private Map<String, List <Transaction>> result;
		
		
	
	
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



