package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import affix.java.effective.moneyservice.Transaction;

public class HQ_MoneyServiceTest {
	
	@Test
	public void testHQMoneyService1() {

		final Map<String, Map<LocalDate, List <Transaction>>> result; 
//		final Map<LocalDate, Map<String, Double>> currencyMap;
		LocalDate date = LocalDate.parse("2020-04-22");
		
		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";
		
		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	
		
		Function<String, String> part
 = ConExApp.projectConfigParsing("test;tester . 2020-", 3);

		String test = "";
		test = part.toString();
		
		boolean t = true;
		if(test.isEmpty()) {
			t = false;
		}
		
		Assert.assertTrue(t);
	}
	
	
}
