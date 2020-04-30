package ya.java.effective.HQ_MoneyService;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses(
	{ 
		HQ_MoneyServiceTest.class,
		ConExAppTest.class,
		RequestTest.class,
	}
)
public class JUnitAllTests {;}