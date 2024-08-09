package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;
import java.util.HashMap;
import java.util.Map;

import static utils.RetryAnalyzer.getRunCount;
import static utils.TestListener.getTestMethodName;

public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentManager.createExtentReports();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static String getTestDescription(ITestResult iTestResult) {
        return iTestResult.getInstance().getClass().getSimpleName() + " - " + iTestResult.getMethod().getDescription();
    }

    public static synchronized void startTest(ITestResult iTestResult) {
        String testName = getTestDescription(iTestResult) + "#" + getRunCount(iTestResult);
        String className = iTestResult.getInstance().getClass().getSimpleName();
        ExtentTest test = extent.createTest(testName, getTestMethodName(iTestResult)).assignCategory(className);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
    }

    public static void removeTest(ITestResult iTestResult) {
        extent.removeTest(getTestDescription(iTestResult) + "#" + (getRunCount(iTestResult) - 1));
    }

    public static void flushTest() {
        extent.flush();
    }
}
