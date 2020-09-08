package practice.independentTestingSuite.support.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.AbstractStructure;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import net.bsmch.Screenshot;
import net.bsmch.util.ExceptionUtil;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import support.drivermanager.DriverManager;
import support.reportermanager.ReporterManager;

import java.lang.reflect.Field;
import java.util.List;

public class ExtentUtil {
    @SuppressWarnings("unchecked")
    public static List<Log> getLogListFromTest(ExtentTest test) {
        try {
            Field listField = AbstractStructure.class.getDeclaredField("list");
            listField.setAccessible(true);
            return (List<Log>) listField.get(test.getModel().getLogContext());
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void addScreenshotToTest(ExtentTest test, ITestResult result, Status status) {
        Throwable exception = result.getThrowable();

        String screenshotName = result.getName() + result.getStartMillis();
        MediaEntityModelProvider screenshot =
                Screenshot.captureScreen(screenshotName, DriverManager.getDriver());
        String screenshotLabel = MarkupHelper.createLabel("Screenshot", ExtentColor.RED).getMarkup() + "<br>";

        test.log(status, screenshotLabel, screenshot);
    }
}
