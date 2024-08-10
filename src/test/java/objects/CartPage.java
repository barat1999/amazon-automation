package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Logger;
import utils.ScreenshotManager;

public class CartPage extends PageObject{
    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    ScreenshotManager screenshotManager = new ScreenshotManager();

    public By itemsInCartCounter = By.xpath("//span[@id='nav-cart-count']");
    By goToCartButton = By.xpath("//span[contains(@class,'nav-cart-icon')]");
    By productNameInCart = By.xpath("//span[@class='a-truncate-cut']");

    //method to verify if the added product and product in cart are the same
    public boolean verifyProductInCart() throws Exception {
        try{
            ProductPage ProductPage = new ProductPage(driver);
            retryClick(goToCartButton);
            waitUntilPresent(productNameInCart);
            String currentUrl = getCurrentPageUrl(driver);
            Logger.info("Current Page Url - " + currentUrl);
            if(currentUrl.contains("nav_cart") && (isElementExists(By.xpath("//img[contains(@alt,'" + ProductPage.getProductName() + "')]")))){
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            Logger.testFail("verifyProductInCart method failed " + e.getMessage());
            screenshotManager.takeScreenshot(driver);
            return false;
        }
    }

    //method to get cart items count
    public int getCartCount() throws Exception {
        int number = -1;
        try {
            WebElement cartCountElement = driver.findElement(itemsInCartCounter);
            String text = cartCountElement.getText();
            number = Integer.parseInt(text.replaceAll("[^0-9]", ""));//regular expression to remove all non-numeric characters
            return number;
        } catch (NoSuchElementException e) {
            Logger.error("Cart count element not found: " + e.getMessage());
            return number;
        } catch (Exception e) {
            Logger.error("getItemsInCartCount method failed: " + e.getMessage());
            return number;
        }
    }
}
