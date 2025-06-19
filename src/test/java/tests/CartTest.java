package tests;

import com.aventstack.extentreports.*;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import pages.CartPage;
import pages.ProductPage;

import java.time.Duration;
import java.util.List;

public class CartTest {
    WebDriver driver;
    WebDriverWait wait;
    ProductPage productPage;
    CartPage cartPage;
    ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/men/tops-men/hoodies-and-sweatshirts-men.html");
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void testAddTwoProductsAndRemoveOne() {
        test = ExtentReportManager.createTest("Test: Add Two Products and Remove One");

        try {
            test.info("Step 1: Click first product");
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".product-items > li:nth-of-type(1) a.product-item-link"))).click();

            test.info("Step 2: Select size and color");
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();

            test.info("Step 3: Add first product to cart");
            productPage.clickAddToCart(wait);
            Assert.assertTrue(productPage.waitForSuccessMessage(wait), "First product was not added.");
            test.pass("First product added successfully");

            driver.navigate().back();
            driver.navigate().refresh();

            test.info("Step 4: Add second product");
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".product-items > li:nth-of-type(2) a.product-item-link"))).click();
            productPage.selectFirstAvailableSize();
            productPage.selectFirstAvailableColor();
            productPage.clickAddToCart(wait);
            Assert.assertTrue(productPage.waitForSuccessMessage(wait), "Second product was not added.");
            test.pass("Second product added successfully");

            test.info("Step 5: Open cart and remove one item");
            cartPage.openCart();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart.item")));
            List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart.item"));
            int itemsBefore = cartItems.size();

            cartPage.removeOneItem();
            wait.until(driver1 -> driver1.findElements(By.cssSelector(".cart.item")).size() < itemsBefore);
            int itemsAfter = driver.findElements(By.cssSelector(".cart.item")).size();

            Assert.assertTrue(itemsAfter < itemsBefore, "Item was not removed successfully.");
            test.pass("Product removed successfully");

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testProductSearch() {
        test = ExtentReportManager.createTest("Test: Product Search");

        test.info("Navigating to Magento homepage");
        driver.get("https://magento.softwaretestingboard.com/");
        WebElement searchBox = driver.findElement(By.id("search"));

        test.info("Searching for 'Hoodie'");
        searchBox.sendKeys("Hoodie");
        searchBox.sendKeys(Keys.ENTER);

        boolean resultDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-item-info"))).isDisplayed();

        Assert.assertTrue(resultDisplayed, "No search results found.");
        test.pass("Search results displayed successfully");
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
