package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Nifty50Page {
    private WebDriver driver;
    private WebDriverWait wait;

    public Nifty50Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void selectStock(String stockName) {
        String xpath = String.format("//span[@class='indexName' and text()='%s']/ancestor::a", stockName);
        WebElement stockLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stockLink);
        wait.until(ExpectedConditions.elementToBeClickable(stockLink));
        stockLink.click();
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'As on ')]")));
    }

    public void scrollToTop5Stocks() {
        int maxScrolls = 20;
        int scrollAmount = 300;
        
        for (int i = 0; i < maxScrolls; i++) {
            try {
                WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Top 5 Stocks')]"));
                if (element.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    break;
                }
            } catch (Exception e) {
            }
            
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollAmount + ");");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
