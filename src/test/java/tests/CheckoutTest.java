package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.ExtentReportManager;

import java.time.Duration;

public class CheckoutTest {
    WebDriver driver;
    WebDriverWait wait;
    SearchResultsPage searchResults;
    ProductPage productPage;
    CheckoutPage checkoutPage;
    ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        searchResults = new SearchResultsPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testCompleteCheckout() {
        test = ExtentReportManager.createTest("Test: Complete Checkout");

        try {
            test.info("Step 1: Search for 'Hoodie'");
            driver.findElement(By.id("search")).sendKeys("Hoodie" + Keys.ENTER);

            test.info("Step 2: Select first product from results");
            searchResults.selectFirstProduct();

            test.info("Step 3: Select size and color, add to cart");
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();
            productPage.clickAddToCart(wait);
            Assert.assertTrue(productPage.waitForSuccessMessage(wait), "Product not added to cart");
            test.pass("Product successfully added to cart");

            test.info("Step 4: Proceed to checkout");
            driver.findElement(By.cssSelector(".showcart")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.action.viewcart"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkout-methods-items button"))).click();

            test.info("Step 5: Fill shipping and place order");
            checkoutPage.fillShippingInformation();
            checkoutPage.selectShippingMethod();
            checkoutPage.clickNext();
            checkoutPage.placeOrder();

            test.info("Step 6: Confirm success message");
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("base")));
            Assert.assertTrue(successMessage.getText().contains("Thank you for your purchase!"), "Order not completed");
            test.pass("Order completed successfully");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testProductSearch() {
        test = ExtentReportManager.createTest("Test: Product Search");
        try {
            test.info("Opening homepage...");
            WebElement searchBox = driver.findElement(By.id("search"));

            test.info("Searching for 'Hoodie'");
            searchBox.sendKeys("Hoodie" + Keys.ENTER);

            boolean resultFound = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".product-item-info"))).isDisplayed();

            Assert.assertTrue(resultFound, "Search result not found");
            test.pass("Search successful and results displayed");

        } catch (Exception e) {
            test.fail("Search test failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void tearDownReport() {
        ExtentReportManager.flushReport();
    }
}
