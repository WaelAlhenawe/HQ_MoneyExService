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

		LocalDate.parse("2020-04-22");

		final Map<String, Map<LocalDate, List <Transaction>>> result; 
		//		final Map<LocalDate, Map<String, Double>> currencyMap;
		LocalDate date = LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		List<Transaction> t = HQ_MoneyService.readObject("Report_SOUTH_2020-04-20.ser");
//		HQ_MoneyService.presentFileContents(t);


		HQ x = new HQ_MoneyService(currencyMap);

		x.filteredTran(req, "..//");

		System.out.println(currencyMap);

		x.printFilteredMap();
		x.profitStatistic(req);

		Assert.fail();
	}


	@Test
	public void testHQMoneyService2() {


		int dayC = HQ_MoneyService.dayCounter("DAY");

		double result = dayC;
		double check = 1.0;

		Assert.assertEquals(check, result, 0.00);
	}

	@Test
	public void testHQMoneyService3() {


		int dayC = HQ_MoneyService.dayCounter("WEEK");

		double result = dayC;
		double check = 7.0;

		Assert.assertEquals(check, result, 0.00);
	}

	@Test
	public void testHQMoneyService4() {


		int dayC = HQ_MoneyService.dayCounter("MONTH");

		double result = dayC;
		double check = 30.0;

		Assert.assertEquals(check, result, 0.00);
	}

	@Test
	public void testHQMoneyService5() {

		LocalDate.parse("2020-04-22");

		final Map<String, Map<LocalDate, List <Transaction>>> result; 
		//		final Map<LocalDate, Map<String, Double>> currencyMap;
		LocalDate date = LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		List<Transaction> t = HQ_MoneyService.readObject("Report_SOUTH_2020-04-20.ser");
//		HQ_MoneyService.presentFileContents(t);

		boolean check = true;
		if(t.isEmpty()) {
			check = false;
		}
		Assert.assertTrue(check);
	}

	@Test
	public void testHQMoneyService6() {

		LocalDate.parse("2020-04-22");

		final Map<String, Map<LocalDate, List <Transaction>>> result; 
		//		final Map<LocalDate, Map<String, Double>> currencyMap;
		LocalDate date = LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		boolean check = true;
		if(currencyMap.isEmpty()) {
			check = false;
		}
		Assert.assertTrue(check);
	}


	@Test
	public void testHQMoneyService7() {

		LocalDate.parse("2020-04-22");

		final Map<String, Map<LocalDate, List <Transaction>>> result = null; 
		//		final Map<LocalDate, Map<String, Double>> currencyMap;
		LocalDate date = LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		Request req = new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		Map<LocalDate, Map<String, Double>> currencyMap = ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		List<Transaction> t = HQ_MoneyService.readObject("Report_SOUTH_2020-04-20.ser");
//		HQ_MoneyService.presentFileContents(t);


		HQ x = new HQ_MoneyService(currencyMap);

		x.filteredTran(req, "..//");
//
//		System.out.println(currencyMap);
//
		x.printFilteredMap();
		x.profitStatistic(req);

		boolean check = x.equals(x);
		
//		boolean check = true;
//		if(x.isEmpty()) {
//			check = false;
//		}
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
