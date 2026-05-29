package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "phone")
    WebElement phoneInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    @FindBy(id = "errorMsg")
    WebElement errorBanner;

    public LoginPage setPassword(String password) {
        waitForVisibility(passwordInput);
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage setPhone(String phone){
        waitForVisibility(phoneInput);
        phoneInput.sendKeys(phone);
        return this;
    }

    public LoginPage clickLogin() {
        waitForClickability(loginButton);
        loginButton.click();
        return this;
    }

    public boolean isLoginButtonDisabled(){
        waitForVisibility(loginButton);
        return !loginButton.isEnabled();
    }

    public boolean isErrorBannerVisible(){
        waitForVisibility(errorBanner);
        return errorBanner.isDisplayed();
    }

    public String getErrorMsg(){
        waitForVisibility(errorBanner);
        return errorBanner.getText();
    }


    public void navigate(String route){
        super.navigate(route);
    }

    public void validLoginAs(String phone, String password) throws InterruptedException {
        this.setPhone(phone)
            .setPassword(password)
                .clickLogin();
        wait.until(d -> d.getCurrentUrl().contains("/dashboard"));
    }

    public void loginAs(String phone, String password) throws InterruptedException {
        this.setPhone(phone)
                .setPassword(password)
                .clickLogin();
    }

}
