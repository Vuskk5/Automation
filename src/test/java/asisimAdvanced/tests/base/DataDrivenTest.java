package asisimAdvanced.tests.base;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class DataDrivenTest extends BaseTest {
    @BeforeMethod(alwaysRun = true)
    @Override
    public void initialize(ITestContext context) {
        super.initialize(context);
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void terminate() {
        super.terminate();
    }
}
