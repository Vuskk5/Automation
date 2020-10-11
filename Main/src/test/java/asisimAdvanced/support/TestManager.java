package asisimAdvanced.support;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.service.ExtentService;
import com.aventstack.extentreports.testng.listener.commons.ExtentTestCommons;
import net.bsmch.util.StringUtil;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestManager {
    private static Map<ITestResult, ExtentTest> testMap = new HashMap<>();
    private static ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> dataProviderTest = new ThreadLocal<>();

    public static synchronized ExtentTest createTest(ITestResult result) {
        String methodName = StringUtil.toTitleCase(result.getMethod().getMethodName());

        // Is the test a DataProvider test?
        if (result.getParameters().length > 0) {
            // Is it the first iteration?
            if (methodTest.get() == null || !methodTest.get().getModel().getName().equals(methodName)) {
                createTestFromResult(result);
            }

            // Create node for parameter set
            String paramName = Arrays.asList(result.getParameters()).toString();
            ExtentTest paramTest = methodTest.get().createNode(paramName);
            dataProviderTest.set(paramTest);
        }
        // Not a DataProvider test
        else {
            dataProviderTest.set(null);
            createTestFromResult(result);
        }

        testMap.put(result, methodTest.get());

        return methodTest.get();
    }

    private static synchronized ExtentTest createTestFromResult(ITestResult result) {
        String methodName = StringUtil.toTitleCase(result.getMethod().getMethodName());
        String desc = result.getMethod().getDescription();

        ExtentTest test = ExtentService.getInstance().createTest(methodName, desc);

        methodTest.set(test);
        String[] groups = result.getMethod().getGroups();
        ExtentTestCommons.assignGroups(test, groups);
        return test;
    }

    public static synchronized void log(ITestResult result) {
        String msg = "Test ";
        Status status = Status.PASS;

        switch (result.getStatus()) {
            case ITestResult.SKIP:
                status = Status.SKIP;
                msg += "skipped";
                break;
            case ITestResult.FAILURE:
                status = Status.FAIL;
                msg += "failed";
                break;
            default:
                msg += "passed";
                break;
        }

        // If a current test does not exist
        if (TestManager.getTest() == null) {
            TestManager.createTest(result);
        }

        // If there was an exception in this test
        if (result.getThrowable() != null) {
            TestManager.getTest().log(status, result.getThrowable());
            return;
        }

        TestManager.getTest().log(status, msg);
    }

    /**
     * Returns the {@link ExtentTest} that was created for the given {@link ITestResult}
     * @param result the TestNG's test result
     * @return the associated ExtentTest
     */
    public static synchronized ExtentTest getTest(ITestResult result) {
        return testMap.get(result);
    }

    /**
     * Returns the latest created ExtentTest.
     * @return the latest test
     */
    public static synchronized ExtentTest getTest() {
        return dataProviderTest.get() == null
                ? methodTest.get()
                : dataProviderTest.get();
    }
}
