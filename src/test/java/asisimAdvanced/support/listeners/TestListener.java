package asisimAdvanced.support.listeners;

import asisimAdvanced.support.util.ExtentUtil;
import asisimAdvanced.support.util.json.JsonUtil;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.util.ExceptionUtil;
import net.bsmch.util.StringUtil;
import org.testng.ITestResult;

public class TestListener extends ExtentITestListenerAdapter {
    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentTestManager.createMethod(result);

        for (Object parameter : result.getParameters()) {
            Markup objectName = MarkupHelper.createLabel(parameter.getClass().getName(), ExtentColor.PURPLE);
            Markup code = MarkupHelper.createCodeBlock(JsonUtil.objectToJson(parameter), CodeLanguage.JSON);
            ExtentTestManager.getTest().info(objectName.getMarkup() + "<br>" + code.getMarkup());
        }
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ExtentTestManager.log(result);

        ExtentTest test = ExtentTestManager.getTest();
        renameTest(test, StringUtil.toTitleCase(result.getName()));
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        ExtentTestManager.log(result);

        ExtentTest test = ExtentTestManager.getTest();
        renameTest(test, StringUtil.toTitleCase(result.getName()));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        Throwable exception = ExceptionUtil.filterStackTrace(result.getThrowable());

        ExtentTest test = ExtentTestManager.getTest();

        // Log the exception
        test.fail(exception);

        // Add a screenshot
        if (DriverManager.getDriver() != null) {
            ExtentUtil.addScreenshotToTest(test, result);
        }

        renameTest(test, StringUtil.toTitleCase(result.getName()));
    }

    private void renameTest(ExtentTest test, String newName) {
        test.getModel().setName(newName);
    }
}
