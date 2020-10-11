package asisimAdvanced.tests.base;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class IndependentTest extends BaseTest {
    @BeforeTest(alwaysRun = true)
    @Override
    public void initialize(ITestContext context) {
        super.initialize(context);
    }

    @AfterTest(alwaysRun = true)
    @Override
    public void terminate() {
        super.terminate();
    }
}
