package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AssertionManager {
    public static void assertElementExists(WebDriver driver, By locator, String successMessage, String failureMessage) {
        try {
            WebElement element = driver.findElement(locator);
            if (element != null) {
                Logger.testPass(successMessage);
                Assert.assertNotNull(element, failureMessage);
            } else {
                Logger.testFail(failureMessage);
                Assert.fail(failureMessage);
            }
        } catch (Exception e) {
            Logger.testFail(failureMessage + " - " + e.getMessage());
            Assert.fail(failureMessage + " - " + e.getMessage());
        }
    }
}
