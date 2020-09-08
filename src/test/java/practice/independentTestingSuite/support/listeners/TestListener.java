package practice.independentTestingSuite.support.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import net.bsmch.util.ExceptionUtil;
import net.bsmch.util.StringUtil;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import support.drivermanager.DriverManager;

import static com.aventstack.extentreports.service.ExtentTestManager.getTest;
import static com.aventstack.extentreports.markuputils.MarkupHelper.createLabel;

public class TestListener extends ExtentITestListenerAdapter {
    static {
        System.setProperty("extent.reporter.spark.start", "true");
        System.setProperty("extent.reporter.spark.config", "src/test/resources/spark-config.xml");
        System.setProperty("extent.reporter.spark.out", "test-output/SparkReport/index.html");
    }

    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        super.onStart(iTestContext);
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
        super.onFinish(iTestContext);
    }

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        ExtentTest test = ExtentTestManager.createMethod(iTestResult);
        String testName = StringUtil.toTitleCase(iTestResult.getName());
        test.getModel().setName(testName);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess(iTestResult);
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        Throwable exception = ExceptionUtil.filterStackTrace(iTestResult.getThrowable());

        Status status;
        ExtentColor color;

        if (exception instanceof AssertionError || exception instanceof TimeoutException) {
            status = Status.FAIL;
            color = ExtentColor.RED;
        }
        else {
            status = Status.ERROR;
            color = ExtentColor.AMBER;
        }

        ExtentTest test = getTest().createNode(createLabel("Fail", color).getMarkup());

        // Log the exception
        test.log(status, exception);

        // Add a screenshot
        if (DriverManager.getDriver() != null) {
            ExtentUtil.addScreenshotToTest(test, iTestResult, status);
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped(iTestResult);
    }
}
