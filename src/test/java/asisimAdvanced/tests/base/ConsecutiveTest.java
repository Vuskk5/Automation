package asisimAdvanced.tests.base;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class ConsecutiveTest extends BaseTest {
    @BeforeMethod(alwaysRun = true)
    @Override
    public void initialize(ITestContext context) {
        super.initialize(context);
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void terminate() {
        super.terminate();
    }
}
