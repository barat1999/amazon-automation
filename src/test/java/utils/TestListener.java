package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.ExtentManager.extentReports;
import static utils.ExtentTestManager.flushTest;

public class TestListener implements ITestListener {
    static String getTestMethodName(ITestResult result) {
        String className = result.getInstance().getClass().getSimpleName();
        String methodName = result.getMethod().getConstructorOrMethod().getName();
        return className + " #" + methodName;
    }

    @Override
    public void onFinish(ITestContext result) {
        extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Logger.info(getTestMethodName(result) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logger.testPass(getTestMethodName(result) + " test is completed.");
        flushTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logger.error("Exception: " + result.getThrowable().getMessage());
        flushTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null)
            Logger.error("Exception: " + throwable.getMessage());
        else
            Logger.testSkip("Test Skipped");
        flushTest();
    }
}
