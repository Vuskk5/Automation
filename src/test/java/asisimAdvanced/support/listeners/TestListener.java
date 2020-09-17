package asisimAdvanced.support.listeners;

import asisimAdvanced.support.util.ExtentUtil;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.service.ExtentService;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.util.ExceptionUtil;
import net.bsmch.util.StringUtil;
import org.testng.ITestResult;

;

public class TestListener extends ExtentITestListenerAdapter {
    static {
        System.setProperty("extent.reporter.spark.start", "true");
        System.setProperty("extent.reporter.spark.config", "src/test/resources/spark-config.xml");
        System.setProperty("extent.reporter.spark.out", "test-output/SparkReport/index.html");
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentTest test = ExtentTestManager.createMethod(result);
        String testName = StringUtil.toTitleCase(result.getName());
        test.getModel().setName(testName);
        ExtentService.getInstance().flush();
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        Throwable exception = ExceptionUtil.filterStackTrace(iTestResult.getThrowable());

        ExtentTest test = ExtentTestManager.getTest();

        // Log the exception
        test.fail(exception);

        // Add a screenshot
        if (DriverManager.getDriver() != null) {
            ExtentUtil.addScreenshotToTest(test, iTestResult);
        }
    }
}
