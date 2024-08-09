package utils;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;

import static utils.ExtentTestManager.getTest;

public class Logger {
    private static final org.apache.logging.log4j.Logger Log = LogManager.getLogger(Logger.class);

    public static void info(String message) {
        Log.info(message);
        if (getTest() != null) {
            getTest().info(message);
        }
    }

    public static void warn(String message) {
        Log.warn(message);
        if (getTest() != null) {
            getTest().warning(message);
        }
    }

    public static void error(String message) {
        Log.error(message);
        if (getTest() != null) {
            getTest().fail(message);
        }
    }

    public static void testPass(String message) {
        getTest().log(Status.PASS, message);
        Log.info(message);
    }

    public static void testFail(String message) {
        getTest().log(Status.FAIL, message);
        Log.error(message);
    }

    public static void testSkip(String message) {
        getTest().log(Status.SKIP, message);
    }
}
