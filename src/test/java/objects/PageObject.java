package objects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Logger;

import java.time.Duration;
import java.util.List;

public abstract class PageObject {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public PageObject(WebDriver webDriver) {
        this.driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public <T> void clear(T locator) throws Exception {
        if (locator instanceof By) {
            waitUntilClickable((By) locator).click();
            driver.findElement((By) locator).clear();
        } else if (locator instanceof WebElement) {
            ((WebElement) locator).click();
            ((WebElement) locator).clear();
        } else {
            throw new Exception("sendKeys failed. Locator type not found");
        }
    }

    public <T> void click(T locator) throws Exception {
        if (locator instanceof By) {
            waitUntilClickable((By) locator).click();
        } else if (locator instanceof WebElement) {
            ((WebElement) locator).click();
        }
    }

    public <T> void clickJs(T locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        if (locator instanceof By) {
            jsExecutor.executeScript("arguments[0].click();", driver.findElement((By) locator));
        } else if (locator instanceof WebElement) {
            jsExecutor.executeScript("arguments[0].click();", locator);
        }
    }

    public void acceptAlert() {
        waitUntilAlertIsPresent().accept();
    }

    public void doubleClick(By locator) {
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(locator)).perform();
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public String getAlertText() {
        return waitUntilAlertIsPresent().getText();
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public <T> String getAttribute(T locator, String attributeName) throws Exception {
        if (locator instanceof By) {
            return waitUntilPresent((By) locator).getAttribute(attributeName);
        } else if (locator instanceof WebElement) {
            return ((WebElement) locator).getAttribute(attributeName);
        } else {
            throw new Exception("check isDisplayed failed. Locator type not found");
        }
    }

    public String getText(By locator) {
        waitUntilPresent(locator);
        return waitUntilVisible(locator).getText();
    }

    public void hover(By locator) {
        waitUntilPresent(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(locator)).perform();
    }

    public boolean isElementExists(By by) {
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            Logger.info("Element not found, By " + by);
            return false;
        }
        return true;
    }

    public void scrollToTop() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            Logger.warn("error in scrollToTop method");
            Logger.warn(e.toString());
        }
    }

    public void navigateTo(String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            driver.navigate().refresh();
        }
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void selectDropdownByVisibleText(By locator, String text) {
        WebElement dropdownElement = driver.findElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(text);
    }

    public void selectDropdownByValue(By locator, String value) {
        WebElement dropdownElement = waitUntilPresent(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
    }

    public <T> void sendKeys(T locator, T keys) throws Exception {
        WebElement element;
        if (locator instanceof By) {
            element = waitUntilPresent((By) locator);
        } else if (locator instanceof WebElement) {
            element = (WebElement) locator;
        } else {
            throw new Exception("sendKeys failed. Locator not found");
        }

        if (keys instanceof String) {
            if (((String) keys).length() > 500) {
                sendKeysJs(element, (String) keys);
            } else {
                element.sendKeys((String) keys);
            }
        } else if (keys instanceof Keys) {
            element.sendKeys((Keys) keys);
        } else {
            throw new Exception("sendKeys failed. Keys type not found");
        }
    }

    public <T> void sendKeysJs(T locator, String keys) throws Exception {
        WebElement element;
        if (locator instanceof By) {
            element = waitUntilPresent((By) locator);
        } else if (locator instanceof WebElement) {
            element = (WebElement) locator;
        } else {
            throw new Exception("sendKeysJs failed. Locator type not found");
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, keys);
    }

    public void setAttribute(By locator, String attributeName, String newValue) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                        element, attributeName, newValue);
    }

    public <T> void scrollToElementJs(T locator) throws Exception {
        WebElement element;
        if (locator instanceof By) {
            element = waitUntilPresent((By) locator);
        } else if (locator instanceof WebElement) {
            element = (WebElement) locator;
        } else {
            throw new Exception("sendKeys failed. Locator type not found");
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void switchToTab(int tabNumber) {
        if (tabNumber < 0) {
            wait.until(ExpectedConditions.numberOfWindowsToBe(tabNumber + 1));
        }
        driver.switchTo().window(driver.getWindowHandles().toArray()[tabNumber].toString());
    }

    public Alert waitUntilAlertIsPresent() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public <T> WebElement waitUntilClickable(T locator) throws Exception {
        if (locator instanceof By) {
            waitUntilPresent((By) locator);
            return wait.until(ExpectedConditions.elementToBeClickable((By) locator));
        } else if (locator instanceof WebElement) {
            return wait.until(ExpectedConditions.elementToBeClickable((WebElement) locator));
        } else {
            throw new Exception("sendKeys failed. Locator type not found");
        }
    }

    public <T> void waitUntilInvisible(T locator) throws Exception {
        if (locator instanceof By) {
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement((By) locator)));
        } else if (locator instanceof WebElement) {
            wait.until(ExpectedConditions.elementToBeClickable((WebElement) locator));
        } else {
            throw new Exception("sendKeys failed. Locator type not found");
        }
    }

    public WebElement waitUntilPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitUntilTitleContains(String title) {
        wait.until(ExpectedConditions.titleContains(title));
    }

    public void waitUntilUrlContains(String url) {
        wait.until(ExpectedConditions.urlContains(url));
    }

    public <T> WebElement waitUntilVisible(T locator) {
        if (locator instanceof By) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated((By) locator));
        } else if (locator instanceof WebElement) {
            return wait.until(ExpectedConditions.visibilityOf((WebElement) locator));
        } else {
            throw new IllegalArgumentException("waitUntilVisible failed. Locator type not found for " + locator);
        }
    }
}
