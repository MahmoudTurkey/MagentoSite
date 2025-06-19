package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class SearchTest {
    WebDriver driver;
    HomePage homePage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        homePage = new HomePage(driver);
    }

    @Test
    public void testValidSearch() {
        homePage.enterSearchKeyword("Hoodie");
        //homePage.clickSearchButton();
        driver.findElement(homePage.searchField).submit();

        // Wait & verify results contain the keyword
        boolean isResultVisible = driver.findElements(By.xpath("//*[contains(text(),'Hoodie')]")).size() > 0;
        Assert.assertTrue(isResultVisible, "Search results do not contain expected keyword.");
    }


@AfterMethod
public void tearDown(ITestResult result) {
    if (ITestResult.FAILURE == result.getStatus()) {
        saveScreenshot();
    }
    quitDriver();
}

@Attachment(value = "Screenshot on Failure", type = "image/png")
public byte[] saveScreenshot() {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
}


// @AfterMethod
// public void tearDown() {
//     quitDriver();
// }

// @Step("Tear down the WebDriver")
// public void quitDriver() {
//     if (driver != null) {
//         try {
//             driver.quit();
//         } catch (Exception e) {
//             System.err.println("Error during driver.quit(): " + e.getMessage());
//             e.printStackTrace();
//         }
//     }



}
