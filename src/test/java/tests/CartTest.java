package tests;

import org.openqa.selenium.By;
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
import pages.CartPage;
import pages.ProductPage;

import java.time.Duration;
import java.util.List;

public class CartTest {
    WebDriver driver;
    WebDriverWait wait;
    ProductPage productPage;
    CartPage cartPage;

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
        // ✅ Add first product (index 1)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-items > li:nth-of-type(1) a.product-item-link")
        )).click();

        productPage.selectFirstAvailableSize();
        productPage.selectFirstAvailableColor();
        productPage.clickAddToCart(wait);
        Assert.assertTrue(productPage.waitForSuccessMessage(wait), "First product was not added.");

        driver.navigate().back();
        driver.navigate().refresh();

        // ✅ Add second product (index 2)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-items > li:nth-of-type(2) a.product-item-link")
        )).click();

        productPage.selectFirstAvailableSize();
        productPage.selectFirstAvailableColor();
        productPage.clickAddToCart(wait);
        Assert.assertTrue(productPage.waitForSuccessMessage(wait), "Second product was not added.");

        // ✅ Open full cart page
        cartPage.openCart();  // Opens full cart via mini-cart → "View and Edit Cart"

        // ✅ Wait for presence of cart items
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart.item")));

        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart.item"));
        int itemsBefore = cartItems.size();
        Assert.assertTrue(itemsBefore >= 2, "Expected at least 2 items in the cart.");

        // ✅ Remove one product
        cartPage.removeOneItem();

        // ✅ Wait until the number of cart items decreases
        wait.until(driver1 -> driver1.findElements(By.cssSelector(".cart.item")).size() < itemsBefore);

        int itemsAfter = driver.findElements(By.cssSelector(".cart.item")).size();
        Assert.assertTrue(itemsAfter < itemsBefore, "Item was not removed successfully from the cart.");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
