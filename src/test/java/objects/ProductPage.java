package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Logger;
import utils.ScreenshotManager;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProductPage extends PageObject{
    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    WebDriver driver;
    private List<Integer> numbers = new ArrayList<>();
    private StringBuilder productName = new StringBuilder();

    //getter and setter defined for StringBuilder
    public void setProductName(String productTitle) {
        productName.setLength(0);
        productName.append(productTitle);
    }

    public String getProductName() {
        return productName.toString();
    }

    ScreenshotManager screenshotManager = new ScreenshotManager();

    public By reviewCountInProductPage = By.xpath("//div[@id='centerCol']//span[@id='acrCustomerReviewText']");
    By reviewCountFieldResultsPage = By.xpath("//span[@class='a-size-base s-underline-text']");
    By addToCartButtonProductPage = By.xpath("//input[@id='add-to-cart-button']");
    By productTitleProductPage = By.xpath("//span[@id='productTitle']");
    By protectionPlanPopupCancelButton = By.xpath("//div[@id='attach-warranty-pane' and not(@class='a-section aok-hidden')]//span[@id='attachSiNoCoverage']");

    // Method to extract numbers from elements
    public void extractNumbers() {
        List<WebElement> elements = driver.findElements(reviewCountFieldResultsPage);

        for (WebElement element : elements) {
            String text = element.getText();
            try {
                int number = Integer.parseInt(text.replaceAll("[^0-9]", "")); // Remove non-numeric characters
                numbers.add(number);
            } catch (NumberFormatException e) {
                Logger.warn("NumberFormatException in extractNumbers method: " + e.getMessage());
            }
        }
    }

    // Method to get the largest number from the list
    public int getLargestNumber() {
        if (numbers.isEmpty()) {
            throw new IllegalStateException("No numbers available to determine the largest number.");
        }
        Collections.sort(numbers);
        return numbers.get(numbers.size() - 1);
    }

    // Formats an integer number into a locale-specific string representation
    public String formatNumber(int number) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(number);
    }

    // Method to get formatted largest number
    public String getFormattedLargestNumber() {
        int largestNumber = getLargestNumber();
        return formatNumber(largestNumber);
    }

    // Method to remove the largest number from the list
    public void removeLargestNumber() {
        if (numbers.isEmpty()) {
            throw new IllegalStateException("No numbers available to remove.");
        }
        int largestNumber = getLargestNumber();

        // Remove the largest number from the list
        numbers.removeIf(number -> number == largestNumber);
    }

    //method to select a product from result page
    public void selectProductWithMostRating() throws Exception {
        try {
            extractNumbers();
            Logger.info("Largest rating found - " + getFormattedLargestNumber());
            waitUntilPresent(By.xpath("//span[@class='a-size-base s-underline-text' and text()='" + getFormattedLargestNumber() + "']")).click();
            scrollToTop();
        } catch (Exception e) {
            Logger.error("selectProductWithMostRating method Failed " + e.getMessage());
            screenshotManager.takeScreenshot(driver);
        }
    }

    //method to add product to cart
    public void addMostRatedProductToCart() throws IOException {
        try{
            if(isElementExists(addToCartButtonProductPage)){
                screenshotManager.takeScreenshot(driver);
                setProductName(getText(productTitleProductPage));
                click(addToCartButtonProductPage);
                if(isElementExists(protectionPlanPopupCancelButton)){
                    waitUntilClickable(protectionPlanPopupCancelButton).click(); 
                }
            }
            else{
                //else logic added to choose the next most reviewed title, if add to cart option NA due to location restrictions
                screenshotManager.takeScreenshot(driver);
                Logger.info("Navigating to the next highest rated product due to location restrictions.");
                driver.navigate().back();
                removeLargestNumber();
                waitUntilPresent(By.xpath("//span[@class='a-size-base s-underline-text' and text()='" + getFormattedLargestNumber() + "']")).click();
                scrollToTop();
                screenshotManager.takeScreenshot(driver);
                addMostRatedProductToCart();
            }
        } catch (Exception e) {
            Logger.error("addMostRatedProductToCart method Failed " + e.getMessage());
            screenshotManager.takeScreenshot(driver);
        }
    }

}
