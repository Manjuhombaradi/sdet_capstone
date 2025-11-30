package com.nse.tests;

import com.nse.pages.NSEHomePage;
import com.nse.pages.StockDetailsPage;
import com.nse.utils.ExtentManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StockVerificationTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(StockVerificationTest.class);

    @DataProvider(name = "stockNames")
    public Object[][] getStockNames() {
        return new Object[][]{
                {"ADANIENT", "2,300.00"},
                {"SUNPHARMA", "1,800.00"},
        };
    }

    @Test(dataProvider = "stockNames")
    public void verifyStockDetails(String stockName, String expectedPrice) {
        ExtentManager.createTest("Verify Stock Details - " + stockName, "Verifying stock information for " + stockName);
        logger.info("Starting test for stock: " + stockName);

        NSEHomePage homePage = new NSEHomePage(driver);
        homePage.open();
        logger.info("Opened NSE India website");

        String homeWindow = driver.getWindowHandle();
        homePage.clickNifty50();
        logger.info("Clicked NIFTY 50 link");

        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> d.getWindowHandles().size() > 1);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!homeWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                logger.info("Switched to Nifty 50 window");
                break;
            }
        }

        com.nse.pages.Nifty50Page niftyPage = new com.nse.pages.Nifty50Page(driver);
        
        niftyPage.waitForPageLoad();
        logger.info("Nifty 50 Page loaded successfully");
        
        niftyPage.scrollToTop5Stocks();
        
        java.util.Set<String> currentHandles = driver.getWindowHandles();
        
        niftyPage.selectStock(stockName);
        logger.info("Selected stock: " + stockName);

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> d.getWindowHandles().size() > currentHandles.size());
            
        for (String windowHandle : driver.getWindowHandles()) {
            if (!currentHandles.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                logger.info("Switched to Stock Details window");
                break;
            }
        }

        StockDetailsPage detailsPage = new StockDetailsPage(driver);
    
        // Capture screenshot before extraction
        String beforeScreenshotPath = captureScreenshot("Before_Extraction_" + stockName);
        if (beforeScreenshotPath != null) {
            ExtentManager.getTest().info("Screenshot Before Extraction", 
                com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(beforeScreenshotPath).build());
        }

        String currentPrice = detailsPage.getCurrentPrice();
        String high52 = detailsPage.get52WeekHigh();
        String low52 = detailsPage.get52WeekLow();

        String afterScreenshotPath = captureScreenshot("After_Extraction_" + stockName);
        if (afterScreenshotPath != null) {
            ExtentManager.getTest().info("Screenshot After Extraction", 
                com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(afterScreenshotPath).build());
        }

        detailsPage.waitForPageLoad();
        logger.info("Stock Details Page loaded successfully");
    
        logger.info("Stock: " + stockName);
        logger.info("Current Price: " + currentPrice);
        logger.info("Expected Price: " + expectedPrice);
        logger.info("52 Week High: " + high52);
        logger.info("52 Week Low: " + low52);

        ExtentManager.getTest().info("Current Price: " + currentPrice);
        ExtentManager.getTest().info("Expected Price: " + expectedPrice);
        ExtentManager.getTest().info("52 Week High: " + high52);
        ExtentManager.getTest().info("52 Week Low: " + low52);

        Assert.assertNotNull(high52, "52 Week High should not be null");
        Assert.assertFalse(high52.isEmpty(), "52 Week High should not be empty");
        
        Assert.assertNotNull(low52, "52 Week Low should not be null");
        Assert.assertFalse(low52.isEmpty(), "52 Week Low should not be empty");
        Assert.assertEquals(currentPrice, expectedPrice, "Current price did not match expected price for " + stockName);

        if (driver.getWindowHandles().size() > 1) {
            driver.close();
            driver.switchTo().window(homeWindow);
        }
    }
}
