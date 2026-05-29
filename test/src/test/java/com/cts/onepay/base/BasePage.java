package com.cts.onepay.base;

import com.cts.onepay.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String BASE_URL = ConfigReader.get("baseUrl");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(ConfigReader.getLong("explicit.wait")));
        PageFactory.initElements(this.driver,this);
    }

    protected void navigate(String route){
        driver.get(BASE_URL + route);
    }

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element).sendKeys(text);
    }

    protected void click(WebElement element) {
        waitForClickability(element).click();
    }

}
