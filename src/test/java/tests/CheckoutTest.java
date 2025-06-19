package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.SearchResultsPage;

import java.time.Duration;

public class CheckoutTest {
    WebDriver driver;
    WebDriverWait wait;
    SearchResultsPage searchResults;
    ProductPage productPage;
    CheckoutPage checkoutPage;

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
        // Step 1: Search for "Hoodie"
        driver.findElement(By.id("search")).sendKeys("Hoodie" + Keys.ENTER);

        // Step 2: Select product
        searchResults.selectFirstProduct();

        // Step 3: Add to cart
        productPage.selectFirstAvailableSize();
        productPage.selectFirstAvailableColor();
        productPage.clickAddToCart(wait);
        Assert.assertTrue(productPage.waitForSuccessMessage(wait), "Product not added to cart");

        // Step 4: Proceed to checkout
        driver.findElement(By.cssSelector(".showcart")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.action.viewcart"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkout-methods-items button"))).click();

        // Step 5: Fill shipping info and complete order
        checkoutPage.fillShippingInformation();
        checkoutPage.selectShippingMethod();
        checkoutPage.clickNext();
        checkoutPage.placeOrder();

        // Step 6: Confirm success message
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("base")));
        Assert.assertTrue(successMessage.getText().contains("Thank you for your purchase!"), "Order not completed");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
