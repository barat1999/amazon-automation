package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverManager;
import utils.Logger;
import utils.ScreenshotManager;

import static utils.ExtentTestManager.startTest;


public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    ScreenshotManager screenshotManager = new ScreenshotManager();

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    public WebDriver getDriver() {
        return this.driver.get();
    }

    @BeforeSuite(alwaysRun = true)
    public void initWebDriver() {
        try {
            setDriver(new DriverManager().getDriver());
            Logger.info("WebDriver initialized successfully.");
        } catch (Exception e) {
            Logger.testFail("Failed to initialize WebDriver: " + e.getMessage());
            throw e;
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult result) {
        try {
            startTest(result);
            Logger.info("Test started: " + result.getMethod().getMethodName());
        } catch (Exception e) {
            Logger.testFail("Failed to start test: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                screenshotManager.takeScreenshot(getDriver());
            }
        } catch (Exception e) {
            Logger.testFail("Failed to take screenshot: " + e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            if (getDriver() != null) {
                getDriver().quit(); // Close the WebDriver instance
                driver.remove(); // Remove the WebDriver instance from ThreadLocal storage
                Logger.info("WebDriver closed successfully.");
            }
        } catch (Exception e) {
            Logger.warn("Failed to close WebDriver: " + e.getMessage());
        }
    }

}