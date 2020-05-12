package ya.java.effective.HQ_MoneyService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;




public class RequestTest {




	private static Request  r1 = null;

	private static String site = "ALL";
	private static  String duration = "DAY";
	private static  LocalDate date = LocalDate.parse("2020-04-22");
	private static  String currency = "EUR";

	@BeforeClass
	public static void setUp() {

		r1 = new Request(site,duration,date,currency);
	}

	@Test
	public void testRequest1() {

		Assert.assertNotNull(r1);
	}

	@Test
	public void testRequest2() {

		Assert.assertEquals(r1.getCurrency(),"EUR");

	}
	@Test
	public void testRequest3() {

		Assert.assertEquals(r1.getDate(),date);

	}

	@Test
	public void testRequest4() {

		Assert.assertEquals(r1.getDuration(),"DAY");

	}

	@Test
	public void testRequest5() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");

		Assert.assertNotNull(r2);

	}

	@Test
	public void testRequest6() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertFalse(r2.hashCode() == r1.hashCode());


	}

	@Test
	public void testRequest7() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Request r3 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertTrue(r2.hashCode() == r3.hashCode());

	}
	@Test
	public void testRequest8() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","",date1,"GBP");
		Assert.assertNotNull(r2);		
	}

	@Test
	public void testRequest9() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertFalse(r2.equals(r1));


	}

	@Test
	public void testRequest10() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertFalse(r2.getDuration().equals(r1.getDuration()));


	}

	@Test
	public void testRequest11() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertFalse(r2.getCurrency().equals(r1.getCurrency()));


	}
	@Test
	public void testRequest12() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL","WEEK",date1,"GBP");
		Assert.assertFalse(r2.getDate().equals(r1.getDate()));


	}
	
	@Test
	public void testRequest13() {

		Request  r2 = new Request("SOUTH",duration,date,currency);

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r3 = new Request("SOUTH","WEEK",date1,"GBP");
		
		Assert.assertFalse(r3.getDate().equals(r2.getDate()));


	}



}












