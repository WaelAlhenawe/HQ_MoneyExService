package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import affix.java.effective.moneyservice.Transaction;

public class ConExAppTest {


	@Test
	public void testConExApp1() {

		LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		Function<String, String> part
		= ConExApp.projectConfigParsing("test tester", 2);
    
		String test = "";
		test = part.toString();
		
		boolean t = true;
		if(test.isEmpty()) {
			t = false;
		}

		Assert.assertTrue(t);
	}


	@Test(expected = AssertionError.class)
	public void testConExApp2() {

		LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		ConExApp.readCurrencyConfig("dunmmy.txt");
		
		Assert.fail("An IOException occurred for file dunmmy.txt");
	}

	@Test(expected = AssertionError.class)
	public void testConExApp3() {

		LocalDate date = LocalDate.parse("2020-03-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		ConExApp.readCurrencyConfigFiles(PeriodChoice, date, "../../");

		Assert.fail();
	}
	
	@Test(expected = AssertionError.class)
	public void testConExApp4() {

		LocalDate.parse("2020-04-22");

		String SiteChoice = "ALL";
		String PeriodChoice = "DAY";
		String StartDay_Period = "2020-04-20";

		new Request(SiteChoice, PeriodChoice, LocalDate.parse(StartDay_Period), "GBP");
		ConExApp.readCurrencyConfigFiles(PeriodChoice, LocalDate.parse(StartDay_Period), "..//").get();	

		ConExApp.readSiteNamesConfig("dunmmy.txt");
		
		Assert.fail("An IOException occurred for file dunmmy.txt");
	}
	
}
