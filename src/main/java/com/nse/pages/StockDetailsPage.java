package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StockDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public StockDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    public String getCurrentPrice() {
           WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(@class, 'symbol-value') and contains(@class, 'norm-ltp')]/span[2]")
        ));
        return element.getText();
    }

    public String get52WeekHigh() {
        scrollToPriceInformation();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(), '52 Week High')]/following-sibling::div")
        ));
        return element.getText();
    }

    public String get52WeekLow() {
        scrollToPriceInformation();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(), '52 Week Low')]/following-sibling::div")
        ));
        return element.getText();
    }

    private void scrollToPriceInformation() {
        try {
            WebElement priceInfoSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(), 'Price Information')] | " +
                             "//span[contains(text(), 'Price Information')] | " +
                             "//div[contains(text(), 'Price Information')]")
            ));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", priceInfoSection);
        } catch (Exception e) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("week52highVal")));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            } catch (Exception ex) {
                System.out.println("Could not scroll to 52-week high element either: " + ex.getMessage());
            }
        }
    }
    public void waitForPageLoad() {
        // Wait for the text "As on " to be visible to confirm page load as requested
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'As on ')]")));
    }
}
