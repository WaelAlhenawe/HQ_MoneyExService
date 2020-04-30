package ya.java.effective.HQ_MoneyService;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.junit.Test;

public class ConExAppTest {
	

	@Test
	public void openNotExistingProjectConfigFile() {
		assertEquals(ConExApp.readProjectConfig("Test.txt"),Optional.empty());

	}
	@Test
	public void outputAfterProjectConfigFileParsing() {
		Map <String, String>  temp= new TreeMap<>();
		temp.putIfAbsent("CurrencyConfig", "CurrencyConfigTest_2020-04-01.txt");
		temp.putIfAbsent("USD", "2000");
		temp.putIfAbsent("SEK", "35000");
		temp.putIfAbsent("ReferenceCurrency", "SEK");

		assertEquals( ConExApp.readProjectConfig("ProjectConfigTest.txt"),Optional.of(temp));

	}
	
	@Test
	public void openNotExistingCurrencyConfigFile() {
		assertEquals(ConExApp.readCurrencyConfig("Test.txt"),Optional.empty());

	}
	@Test
	public void outputAfterCurrencyConfigFileParsing() {
		Map <String, Currency>  temp2= new TreeMap<>();
		temp2.putIfAbsent("USD", new Currency("USD", 9.9638));

		assertEquals( ConExApp.readCurrencyConfig("CurrencyConfigTest_2020-04-01.txt"),Optional.of(temp2));

	}

}
