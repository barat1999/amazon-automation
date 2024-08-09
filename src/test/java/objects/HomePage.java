package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Logger;
import utils.ScreenshotManager;

public class HomePage extends PageObject{
    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    ScreenshotManager screenshotManager = new ScreenshotManager();

    By searchBarHomePage = By.xpath("//input[@id='twotabsearchtextbox']");
    By searchButton = By.xpath("//span[@id='nav-search-submit-text']");
    //Element to verify the search functionality
    public By resultsPageHeader = By.xpath("//h2[contains(text(),'Results')]");

    //method to search any keyword in homepage
    public void searchForProduct(String keyword) throws Exception {
        try {
            isElementExists(searchBarHomePage);
            sendKeys(searchBarHomePage, keyword);
            waitUntilClickable(searchButton).click();
        } catch (Exception e) {
            Logger.testFail("searchForProduct method failed " + e.getMessage());
            screenshotManager.takeScreenshot(driver);
        }
    }
}
