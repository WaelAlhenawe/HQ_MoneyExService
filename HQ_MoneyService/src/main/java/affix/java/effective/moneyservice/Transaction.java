package affix.java.effective.moneyservice;

import java.time.LocalDateTime;
import java.util.logging.Logger;



public final class Transaction implements java.io.Serializable {
	
		public enum TransactionMode {

	}
		private static final long serialVersionUID = 1L;
//		Order confirmedOrder;
//		double commission;
		private final int id;
		private final LocalDateTime timeStamp;
		private final String currencyCode;
		private final int amount;
		private final TransactionMode mode;
		private static int uniqueId = 1;
		
		

		// Set up a logger
		private static Logger logger;
					
		static{
			logger = Logger.getLogger("affix.java.effective.moneyservice");
			}
		
		public Transaction(LocalDateTime timeStamp, String currencyCode, int amount, TransactionMode mode) {
			
			this(uniqueId++, timeStamp, currencyCode, amount, mode);
		}
		/**
		 * @param id
		 * @param timeStamp
		 * @param currencyCode
		 * @param amount
		 * @param mode
		 */
		public Transaction(int id, LocalDateTime timeStamp, String currencyCode, int amount, TransactionMode mode) {
			super();
			this.timeStamp = timeStamp;
			this.currencyCode = currencyCode;
			this.amount = amount;
			this.mode = mode;
			this.id = id;
		}
		

		
		/**
		 * @return the id
		 */
		public synchronized int getId() {
			return id;
		}
		/**
		 * @return the timeStamp
		 */
		public synchronized LocalDateTime getTimeStamp() {
			return timeStamp;
		}
		/**
		 * @return the currencyCode
		 */
		public synchronized String getCurrencyCode() {
			return currencyCode;
		}
		/**
		 * @return the amount
		 */
		public synchronized int getAmount() {
			return amount;
		}
		/**
		 * @return the mode
		 */
		public synchronized TransactionMode getMode() {
			return mode;
		}
		@Override
		public int hashCode() {
			
			logger.finest("Transaction hash code used");
			
			final int prime = 31;
			int result = 1;
			result = prime * result + amount;
			result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
			result = prime * result + id;
			result = prime * result + ((mode == null) ? 0 : mode.hashCode());
			result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			
			logger.finest("Transaction equals used ");
			
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Transaction other = (Transaction) obj;
			if (amount != other.amount)
				return false;
			if (currencyCode == null) {
				if (other.currencyCode != null)
					return false;
			} else if (!currencyCode.equals(other.currencyCode))
				return false;
			if (id != other.id)
				return false;
			if (mode != other.mode)
				return false;
			if (timeStamp == null) {
				if (other.timeStamp != null)
					return false;
			} else if (!timeStamp.equals(other.timeStamp))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return String.format("Transaction [id=%s, timeStamp=%s, currencyCode=%s, amount=%s, mode=%s]", id,
					timeStamp, currencyCode, amount, mode);
		}	

}
