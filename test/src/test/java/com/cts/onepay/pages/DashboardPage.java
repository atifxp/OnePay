package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Logout']")
    WebElement btnLogout;

    @FindBy(xpath = "//p[@class='font-semibold text-gray-900 group-hover:text-green-700 transition']")
    WebElement btnLoanApplication;

    public void clickLogout(){
        waitForClickability(btnLogout).click();
        //wait for login page
        wait.until(d -> d.getCurrentUrl().contains("/login"));
    }

    public void clickLoanApplication(){
        waitForClickability(btnLoanApplication).click();
    }

}
