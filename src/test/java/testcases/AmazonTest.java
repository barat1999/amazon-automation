package testcases;

import objects.CartPage;
import objects.HomePage;
import objects.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.AssertionManager;
import utils.Logger;
import utils.TestListener;
import static utils.DataManager.BASE_URL;

@Listeners(TestListener.class)
public class AmazonTest extends BaseTest {

    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    private String productNameInProductPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        homePage = new HomePage(getDriver());
        productPage = new ProductPage(getDriver());
        cartPage = new CartPage(getDriver());
    }

    @Test(priority = 1,description = "Search for Office Chairs")
    public void testcase_1() throws Exception {
        getDriver().get(BASE_URL);
        homePage.searchForProduct("Office chairs");

        AssertionManager.assertElementExists(
                getDriver(),
                homePage.resultsPageHeader,
                "Product search was successful. Search results are displayed.",
                "Product search failed. Search results are not displayed"
        );
        Logger.info("Result page screenshot below");
        screenshotManager.takeScreenshot(getDriver());
    }

    @Test(priority = 2, dependsOnMethods = "testcase_1" , description = "Select the Highest-Rated Chair")
    public void testcase_2() throws Exception {
        productPage.selectProductWithMostRating();

        String reviewCountProductPage = productPage.getText(productPage.reviewCountInProductPage);
        String mostRatedProductCount = productPage.getFormattedLargestNumber();

        if (reviewCountProductPage.startsWith(mostRatedProductCount)) {
            Logger.testPass("Most rated product selected successfully. Review count: " + reviewCountProductPage);

        } else {
            Logger.testFail("Most rated product NOT selected. Expected review count to start with: " + mostRatedProductCount + ", but got: " + reviewCountProductPage);
        }
        screenshotManager.takeScreenshot(getDriver());
    }

    @Test(priority = 3, dependsOnMethods = "testcase_2" , description = "Add Product to Cart")
    public void testcase_3() throws Exception {
        try {
            int defaultCount = cartPage.getCartCount();
            Logger.info("Default items in cart before adding the product: " + defaultCount);
            productPage.addMostRatedProductToCart();
            int itemCount = cartPage.getCartCount();
            if (itemCount == defaultCount + 1) {
                Logger.testPass("Cart successfully updated with 1 item. Current count: " + itemCount);
            } else {
                Logger.testFail("Cart update failed. Expected count: " + (defaultCount+1) + ", but got: " + itemCount);
            }
            screenshotManager.takeScreenshot(getDriver());
        } catch (Exception e) {
            Logger.error("Failed to add product to cart or validate cart item count. " + e.getMessage());
            screenshotManager.takeScreenshot(getDriver());
        }
    }

    @Test(priority = 4, dependsOnMethods = "testcase_3" , description = "Verify Product in Cart")
    public void testcase_4() throws Exception {
        productNameInProductPage = productPage.getProductName();
        boolean productCheck = cartPage.verifyProductInCart();
        Assert.assertTrue(productCheck, "The product is not found in the cart.");
        screenshotManager.takeScreenshot(getDriver());
    }
}

