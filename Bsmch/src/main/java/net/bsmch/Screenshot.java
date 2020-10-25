package net.bsmch;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import net.bsmch.util.StringUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for handling screenshots.
 * All functions return {@link MediaEntityModelProvider} to be used in extent reports.
 */
public class Screenshot {
    private static final String DEFAULT_SCREENSHOT_PATH = System.getProperty("user.dir") + "/test-output/Screenshots/";
    private static String SCREENSHOT_PATH = DEFAULT_SCREENSHOT_PATH;

    /**
     * Deletes all files in {@link Screenshot#SCREENSHOT_PATH} folder.
     * @return {@code true} if the operation was successful, else otherwise.
     */
    public static boolean clearScreenshotDirectory() {
        return FileHandler.delete(new File(SCREENSHOT_PATH));
    }

    /**
     * Creates a directory in {@link Screenshot#SCREENSHOT_PATH}.
     * @return {@code true} if the operation was successful.
     */
    public static boolean createScreenshotDirectory() {
        File directory = new File(SCREENSHOT_PATH);

        if (directory.exists()) {
            return true;
        }

        try {
            return FileHandler.createDir(new File(SCREENSHOT_PATH));
        }
        catch (IOException ex) {
            System.out.println("Could not create screenshot directory.");
            return false;
        }
    }

    /**
     * Sets the path of the saved screenshots.
     * @param screenshotPath The path to be set.
     */
    public static void setScreenshotPath(String screenshotPath) {
        Screenshot.SCREENSHOT_PATH = screenshotPath;
    }

    static {
        clearScreenshotDirectory();
        createScreenshotDirectory();
    }

    /**
     * Takes a screenshot of the given {@code driver} as {@link OutputType#FILE}
     * @param imageName The name of the saved image.
     * @param driver    The driver to capture.
     * @return          {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static MediaEntityModelProvider captureScreen(String imageName ,WebDriver driver) {
        if (driver == null) {
            throw new NullPointerException("WebDriver cannot be null.");
        }

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        return capture(takesScreenshot, imageName);
    }

    /**
     * Takes a screenshot of the given {@code driver}.
     * @param driver    The driver to capture.
     * @param element   The element to highlight.
     * @param imageName The name of the saved image.
     * @return          {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static MediaEntityModelProvider captureElementHighlight(WebDriver driver, WebElement element, String imageName) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        jse.executeScript("arguments[0].style.border='2px solid red'", element);
        MediaEntityModelProvider screenshot = capture(takesScreenshot, imageName);
        jse.executeScript("arguments[0].style.border='none'", element);

        return screenshot;
    }

    /**
     * Takes a screenshot of the given {@code driver} as {@link OutputType#BASE64}
     * @param driver The driver to capture.
     * @return       {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static MediaEntityModelProvider captureScreenBase64(WebDriver driver) {
        return captureScreen(null, driver);
    }

    /**
     * Takes a screenshot of the given {@code driver}.
     * @param driver    The driver to capture.
     * @param element   The element to highlight.
     * @return          {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static MediaEntityModelProvider captureElementHighlightBase64(WebDriver driver, WebElement element) {
        return captureElementHighlight(driver, element, null);
    }

    /**
     * Takes a screenshot of the given {@code element} as {@link OutputType#BASE64}
     * @param element The element to capture.
     * @return      {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static <T extends WebElement> MediaEntityModelProvider captureElementBase64(T element) {
        return captureElement(element, null);
    }

    /**
     * Takes a screenshot of the given {@code element} as {@link OutputType#FILE}
     * @param element The element to capture.
     * @param imageName The name of the saved image.
     * @return      {@link MediaEntityModelProvider} wrapping the captured {@link com.aventstack.extentreports.model.ScreenCapture}
     */
    public static <T extends WebElement> MediaEntityModelProvider captureElement(T element, String imageName) {
        if (element == null) {
            throw new NullPointerException("WebElement cannot be null.");
        }

        if (element.getSize().getWidth() == 0 || element.getSize().getHeight() == 0) {
            return null;
        }

        return capture(element, imageName);
    }

    private static <T extends TakesScreenshot> MediaEntityModelProvider capture(T takesScreenshot, String imageName) {
        if (imageName == null) {
            return captureAsBase64(takesScreenshot);
        }

        return captureAsFile(takesScreenshot, imageName);
    }

    private static <T extends TakesScreenshot> MediaEntityModelProvider captureAsBase64(T takesScreenshot) {
        String screenshotBase64 = takesScreenshot.getScreenshotAs(OutputType.BASE64);

        try {
            return MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build();
        }
        catch (IOException ex) {
            throw new RuntimeException("Could not create a file from the given Base64 String.", ex);
        }
    }

    private static <T extends TakesScreenshot> MediaEntityModelProvider captureAsFile(T takesScreenshot, String imageName) {
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        imageName = StringUtil.forceAlphaNumeric(imageName);

        File destination = new File(DEFAULT_SCREENSHOT_PATH + "/" + imageName + ".png");
        try {
            FileHandler.copy(source, destination);

            return MediaEntityBuilder.createScreenCaptureFromPath(destination.getAbsolutePath()).build();
        }
        catch (IOException ex) {
            throw new RuntimeException("Could not copy screenshot to " + destination.getAbsolutePath(), ex);
        }
    }
}

