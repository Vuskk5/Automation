package practice.independentTestingSuite.tests.base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class SterileTest extends BaseTest {
    @Override
    @BeforeMethod(alwaysRun = true)
    protected void initialize(ITestContext context) {
        super.initialize(context);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    protected void terminate() {
        super.terminate();
    }
}
