package practice.independentTestingSuite.tests.base;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public abstract class ConsecutiveTest extends BaseTest {
    @Override
    @BeforeTest
    public void initialize(ITestContext context) {
        super.initialize(context);
    }

    @Override
    @AfterTest
    public void terminate() {
        super.terminate();
    }
}
