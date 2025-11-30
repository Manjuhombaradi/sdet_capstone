package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NSEHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "input[placeholder*='Search by Company name']")
    private WebElement searchInput;

    public NSEHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.nseindia.com/");
    }

    public void searchStock(String stockName) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(stockName);
        searchInput.sendKeys(Keys.ENTER);
    }

    public void clickNifty50() {
        WebElement nifty50Link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("NIFTY 50")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", nifty50Link);
    }
}
