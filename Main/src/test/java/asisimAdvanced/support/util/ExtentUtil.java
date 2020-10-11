package asisimAdvanced.support.util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.AbstractStructure;
import com.aventstack.extentreports.model.Log;
import net.bsmch.Screenshot;
import org.testng.ITestResult;
import net.bsmch.drivermanager.DriverManager;

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

    public static void addScreenshotToTest(ExtentTest test, ITestResult result) {
        String screenshotName = result.getName() + result.getStartMillis();
        MediaEntityModelProvider screenshot =
                Screenshot.captureScreen(screenshotName, DriverManager.getDriver());

        String screenshotLabel = MarkupHelper.createLabel("Screenshot", ExtentColor.RED).getMarkup() + "<br>";

        test.fail(screenshotLabel, screenshot);
    }
}
