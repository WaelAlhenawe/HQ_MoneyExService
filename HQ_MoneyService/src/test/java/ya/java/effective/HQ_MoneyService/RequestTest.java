package ya.java.effective.HQ_MoneyService;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;




public class RequestTest {




	private static Request  r1 = null;

	private static String site = "ALL";
	private static  LocalDate startDate = LocalDate.of(2020, 04, 20);
	private static  LocalDate endDate = LocalDate.of(2020, 04, 22);
	private static  String currency = "EUR";

	@BeforeClass
	public static void setUp() {

		r1 = new Request(site,endDate,startDate,currency, null);
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

		Assert.assertEquals(r1.getStartDate(),startDate);

	}

	@Test
	public void testRequest4() {

		Assert.assertEquals(r1.getEndDate(),endDate);

	}

	@Test
	public void testRequest5() {

		LocalDate date1 = LocalDate.parse("2020-04-23");
		Request r2 = new Request("ALL",date1.plusWeeks(1),date1,"GBP", null);

		Assert.assertNotNull(r2);

	}

	@Test
	public void testRequest6() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-23");

		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertFalse(r2.hashCode() == r1.hashCode());


	}

	@Test
	public void testRequest7() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",endDate2,startDate2,"EUR", null);
		Assert.assertTrue(r2.hashCode() == r1.hashCode());

	}
	@Test
	public void testRequest8() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertNotNull(r2);		
	}

	@Test
	public void testRequest9() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",startDate2,endDate2,"GBP", null);
		Assert.assertFalse(r2.equals(r1));


	}

	@Test
	public void testRequest10() {

		LocalDate date = LocalDate.parse("2020-04-20");
		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertTrue(r2.getEndDate().equals(date));


	}

	@Test
	public void testRequest11() {
		String cur = "GBP";
		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertFalse(r2.getCurrency().equals(cur));


	}
	@Test
	public void testRequest12() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");

		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertFalse(r2.getStartDate().equals(r1.getStartDate()));


	}

	@Test
	public void testRequest13() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");
		
		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);
		Assert.assertFalse(r2.getEndDate().equals(r1.getEndDate()));


	}

	@Test
	public void testRequest14() {

		LocalDate startDate2 = LocalDate.parse("2020-04-20");
		LocalDate endDate2 = LocalDate.parse("2020-04-22");
		
		Request r2 = new Request("ALL",startDate2,endDate2,"EUR", null);//
//		LocalDate date1 = LocalDate.parse("2020-04-23");
//		Request r3 = new Request("SOUTH","WEEK",date1,"GBP");
		
		String check = "Request [site=[SOUTH, CENTER, NORTH, WEST], startDate=2020-04-22, endDate=2020-04-20, currency=EUR, presentingMode=null]";
		String temp = r2.toString();
		
		boolean xCheck = false;
		if(check.equalsIgnoreCase(temp)) {
			xCheck = true;
			
		}
		
		Assert.assertTrue(xCheck);


	}
	
}












