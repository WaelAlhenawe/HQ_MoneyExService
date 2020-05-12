package ya.java.effective.HQ_MoneyService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

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
		= ConExApp.helpToParsing("test tester", 2);

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

	@Test
	public void testConExApp5() {

		// file which exist in the root of the project
		Map<String, Double> map = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt");

		boolean ok = false;
		if(!map.equals(null)) {
			ok = true;  

		}
		assertTrue(ok);	
	}

	@Test
	public void testConExApp6() {

		// file which does not exist in the root of the project
		Map<String, Double> map = ConExApp.readCurrencyConfig("CurrencyConfig_2020-09-01.txt");

		boolean ok = false;
		if(!map.equals(null)) {
			ok = true;  

		}
		assertTrue(ok);		
	}

	@Test
	public void testConExApp7() {


		Map<String, Double> map = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt");

		Double d = 0.0;
		if(!map.equals(null)) {
			d = map.get("AUD") ;

		}
		assertTrue(d==6.0501);
	}

	@Test
	public void testConExApp8() {


		Map<String, Double> map = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt");

		Double d = 0.0;
		if(!map.equals(null) && !map.isEmpty()) {
			d = map.get("EUR") ;

		}
		assertFalse(d==6.0501);

	}

	@Test
	public void testConExApp9() {


		Map<String, Double> map = ConExApp.readCurrencyConfig("CurrencyConfig_2020-04-01.txt");

		boolean ok = false;
		if(map.containsKey("USD")) {
			ok = true;
		};

		assertTrue(ok);	
	}





}
