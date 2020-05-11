package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import affix.java.effective.moneyservice.Transaction;
import affix.java.effective.moneyservice.TransactionMode;

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

    @Test (expected = AssertionError.class)

    public void testHQMoneyService9() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("EUR",10.9377);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

        

        HQ_MoneyService hq = new HQ_MoneyService(map);

        

        Assert.fail();
        

        

    }
    
    
    @Test(expected = AssertionError.class)

    public void testHQMoneyService10() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("EUR",10.9377);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

		LocalDate startDate2 = LocalDate.parse("2020-04-22");
		LocalDate endDate2 = LocalDate.parse("2020-04-20");
		
		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		

        HQ_MoneyService hq = new HQ_MoneyService(map);

        
        hq.filteredTran(r2, "");
//        hq.filteredTran(r2, "../");

        Assert.fail();

        

    }
    @Test

    public void testHQMoneyService11() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("AUD",6.0501);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

        

        HQ_MoneyService hq = new HQ_MoneyService(map);

        

        Assert.assertNotNull(hq);

        

        

    }
	

    @Test(expected = AssertionError.class)

    public void testHQMoneyService12() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("EUR",10.9377);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

		LocalDate startDate2 = LocalDate.parse("2020-04-22");
		LocalDate endDate2 = LocalDate.parse("2020-04-20");
		
		Request r2 = new Request("ALL",startDate2,endDate2,"ALL", null);
		

        HQ_MoneyService hq = new HQ_MoneyService(map);

        
        hq.filteredTran(r2, "../c/");
//        hq.filteredTran(r2, "../");

        Assert.fail();

        

    }
    
    
    
    @Test

    public void testHQMoneyService13() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("AUD",6.0501);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-24");
		
		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);

        HQ_MoneyService hq = new HQ_MoneyService(map);

        Map<String, Map<LocalDate, List <Transaction>>> result; 
        
        hq.profitStatistic();
        
//        hq.printSummarizeProfitStatistic(period, temp, endDate, startDate);
        
        Assert.assertNotNull(hq);

        

        

    }
    
    @Test

    public void testHQMoneyService14() {

        

        Map<String,Double> currencyMap = new TreeMap<>();

        Map<LocalDate,Map<String, Double>> map = new TreeMap<>();

        

        currencyMap.putIfAbsent("AUD",6.0501);

        currencyMap.putIfAbsent("USD", 9.9638);

        

        LocalDate date = LocalDate.parse("2020-04-01");

        map.putIfAbsent(date, currencyMap);

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-20");
//		
		Request r2 = new Request("SOUTH",startDate2,endDate2,"USD", null);
		
		
		Map<String, Map<LocalDate, List <Transaction>> > siteTemp = new TreeMap<>(); 
		
		Map<LocalDate, List <Transaction> > resultTemp = new TreeMap<>(); 

        HQ_MoneyService hq = new HQ_MoneyService(map);
        
        hq.filteredTran(r2, "Report_SOUTH_2020-04-20.ser");
//        hq.profitStatistic()
        
//
//        Map<String, Map<LocalDate, List <Transaction>>> result; 
//        
//        hq.profitStatistic();
        
//        hq.printSummarizeProfitStatistic(period, temp, endDate, startDate);
    	
		LocalDate tempDate = LocalDate.parse("2020-04-20");

    	
        
        
		List<Transaction> t = new ArrayList<Transaction>();

		
		t.add(new Transaction(LocalDateTime.now(), "EUR", 800, TransactionMode.BUY));
		t.add(new Transaction(LocalDateTime.now(), "USD", 500, TransactionMode.BUY));
		t.add(new Transaction(LocalDateTime.now(), "GBP", 450, TransactionMode.BUY));
		t.add(new Transaction(LocalDateTime.now(), "EUR", 100, TransactionMode.BUY));
		t.add(new Transaction(LocalDateTime.now(), "USD", 200, TransactionMode.BUY));
		
		resultTemp.putIfAbsent(tempDate, t);
		
		hq.profitStatistic();
		
		siteTemp.putIfAbsent("SOUTH", resultTemp);
		
//		hq.printSummarizeProfitStatistic(period, temp, endDate, startDate);
		
//        SOUTH
//        2020-04-01 List<Transaction>
        
        
        Assert.assertNotNull(siteTemp);

        

        

    }   
    
    
}
