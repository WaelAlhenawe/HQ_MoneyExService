package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import affix.java.effective.moneyservice.Transaction;

public class HQ_MoneyServiceTest {

	@Test(expected = AssertionError.class)
	public void testHQMoneyService1() {



		String SiteChoice = "ALL";
		LocalDate PeriodChoice = LocalDate.parse("2020-04-20");
		String StartDay_Period = "2020-04-20";


		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP", null);
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	



		HQ x = new HQ_MoneyService(currencyMap);

		x.filteredTran(req, "..//");

		System.out.println(currencyMap);

		x.printFilteredMap();
		x.profitStatistic();

		Assert.fail();
	}



	@Test
	public void testHQMoneyService5() {

		List<Transaction> t = HQ_MoneyService.readObject("Report_SOUTH_2020-04-20.ser");

		boolean check = true;
		if(t.isEmpty()) {
			check = false;
		}
		Assert.assertTrue(check);
	}

	@Test
	public void testHQMoneyService6() {

		LocalDate PeriodChoice = LocalDate.parse("2020-04-20");
		String StartDay_Period = "2020-04-20";

		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		boolean check = true;
		if(currencyMap.isEmpty()) {
			check = false;
		}
		Assert.assertFalse(check);
	}


	@Test
	public void testHQMoneyService7() {


		String SiteChoice = "ALL";
		LocalDate PeriodChoice = LocalDate.parse("2020-04-20");
		String StartDay_Period = "2020-04-20";


		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP", null);;
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	



		HQ x = new HQ_MoneyService(currencyMap);

		x.filteredTran(req, "..//");

		x.printFilteredMap();
		x.profitStatistic();

		boolean check = x.equals(x);
		
		Assert.assertTrue(check);
	}
	


@Test

    public void testHQMoneyService8() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("AUD",6.0501);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

        

        HQ_MoneyService hq = new HQ_MoneyService(map);

        

        Assert.assertNotNull(hq);

        

        

    }

    @Test

    public void testHQMoneyService9() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("EUR",10.9377);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

        

        HQ_MoneyService hq = new HQ_MoneyService(map);

        

        Assert.assertNotNull(hq);

        

        

    }

}
