package ya.java.effective.HQ_MoneyService;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//import jdk.vm.ci.code.site.Site;
import affix.java.effective.moneyservice.TransactionMode;

public class HQ_MoneyServiceTest {
	
	@Test
	public void testSiteSupport1() {
		
		Map<String, String> cashBoxTemp = new TreeMap<String, String>();
		Map<String, Currency> currencyMapTemp = new TreeMap<String, Currency>();

		cashBoxTemp.putIfAbsent("USD", "2000");
		cashBoxTemp.putIfAbsent("SEK", "20000");

		currencyMapTemp.putIfAbsent("USD", new Currency("USD", 9.9638));
		
		Site temp = new Site(cashBoxTemp, currencyMapTemp, ConExApp.Site_Name);
		
		String filename  = SiteSupport.generateFilename();

		boolean order1 = temp.buyMoney(new Order(1000.00, "USD", TransactionMode.BUY, "SOUTH"));
		boolean order2 = temp.sellMoney(new Order(500.00, "USD", TransactionMode.SELL, "SOUTH"));
		boolean order3 = temp.buyMoney(new Order(100.00, "USD", TransactionMode.BUY, "SOUTH"));
		boolean order4 = temp.sellMoney(new Order(200.00, "USD", TransactionMode.SELL, "SOUTH"));



		temp.shutDownService(null);

//		File f = new File(filename);
//
//		boolean exists = f.exists();
//		
//		SiteSupport.readData(filename);
//		
//		
//		System.out.println(SiteSupport.readData(filename));
		
		Assert.assertTrue(true);
	}
	
	
}
