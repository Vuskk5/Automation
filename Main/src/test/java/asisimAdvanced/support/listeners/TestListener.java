package asisimAdvanced.support.listeners;

import asisimAdvanced.support.TestManager;
import asisimAdvanced.support.util.ExtentUtil;
import asisimAdvanced.support.util.json.JsonUtil;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.service.ExtentService;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.util.ExceptionUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentService.getInstance().setAnalysisStrategy(AnalysisStrategy.TEST);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        ExtentService.getInstance().flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        TestManager.createTest(result);

        // Add object parameters as Json snippets
        for (Object parameter : result.getParameters()) {
            Markup objectName = MarkupHelper.createLabel(parameter.getClass().getSimpleName(), ExtentColor.PURPLE);
            Markup code = MarkupHelper.createCodeBlock(JsonUtil.objectToJson(parameter), CodeLanguage.JSON);
            TestManager.getTest().info(objectName.getMarkup() + "<br>" + code.getMarkup());
        }
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        TestManager.log(result);
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        Throwable exception = ExceptionUtil.filterStackTrace(result.getThrowable());

        ExtentTest test = TestManager.getTest();

        // Log the exception
        test.fail(exception);

        // Add a screenshot
        if (DriverManager.getDriver() != null) {
            ExtentUtil.addScreenshotToTest(test, result);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestManager.log(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        TestManager.log(result);
    }
}
