package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VerificationPage extends BasePage {

    public VerificationPage(WebDriver driver) {
        super(driver);
    }

    //Status Page
    @FindBy(id = "verificationStatus")
    WebElement verificationStatus;

    @FindBy(xpath = "//button[normalize-space()='Logout']")
    WebElement logoutBtn;

    public String getVerificationStatusText(){
        waitForVisibility(verificationStatus);
        return verificationStatus.getText();
    }


    public void clickLogout(){
        waitForClickability(logoutBtn).click();
    }
}
