package ya.java.effective.HQ_MoneyService;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses(
	{ 
		HQ_MoneyServiceTest.class, 
		ConExAppTest.class,
	}
)
public class JUnitAllTests {;}