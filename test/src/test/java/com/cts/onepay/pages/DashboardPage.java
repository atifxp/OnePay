package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    private WebDriver driver;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Logout']")
    WebElement btnLogout;

    public void clickLogout(){
        waitForClickability(btnLogout).click();
        //wait for login page
        wait.until(d -> d.getCurrentUrl().contains("/login"));
    }


}
