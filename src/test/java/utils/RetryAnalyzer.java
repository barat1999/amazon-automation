package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.util.HashMap;
import java.util.Map;

import static utils.TestListener.getTestMethodName;

public class RetryAnalyzer implements IRetryAnalyzer {

    public static Map<String, Integer> runCount = new HashMap<>();
    private static final int MAX_RETRY_COUNT = 2;

    public static Integer getRunCount(ITestResult iTestResult) {
        String testMethodName = getTestMethodName(iTestResult);
        return runCount.get(testMethodName) == null ? 1 : runCount.get(testMethodName);
    }

    public void addRunCount(ITestResult iTestResult) {
        String testMethodName = getTestMethodName(iTestResult);
        runCount.put(testMethodName, getRunCount(iTestResult) + 1);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (getRunCount(result) < MAX_RETRY_COUNT) {
            addRunCount(result);
            return true;
        }
        return false;
    }

}
