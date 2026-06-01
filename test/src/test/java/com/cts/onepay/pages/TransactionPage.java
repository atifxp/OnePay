package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TransactionPage extends BasePage {

    public TransactionPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Send Money']")
    WebElement sendMoneyToggle;

    @FindBy(xpath = "//input[@placeholder=\"Enter receiver's user ID\"]")
    WebElement receiverInput;

    @FindBy(xpath = "//input[@placeholder='Enter amount']")
    WebElement amountInput;

    @FindBy(xpath = "//input[@placeholder=\"What's this for?\"]")
    WebElement messageInput;

    @FindBy(xpath = "//form//button[@type='submit']")
    WebElement sendButton;

    @FindBy(xpath = "//div[contains(@class,'bg-red-50')]")
    WebElement errorBanner;

    @FindBy(xpath = "//div[contains(@class,'bg-green-50')]")
    WebElement successBanner;

    @FindBy(xpath = "(//a[contains(@href,'/transactions/')])[1]")
    WebElement firstTxnRow;



    public TransactionPage openTransferForm() {
        waitForClickability(sendMoneyToggle).click();
        return this;
    }

    public TransactionPage setReceiver(String receiverUserId) {
        waitForVisibility(receiverInput);
        receiverInput.clear();
        receiverInput.sendKeys(receiverUserId);
        return this;
    }

    public TransactionPage setAmount(String amount) {
        waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    public TransactionPage setMessage(String message) {
        waitForVisibility(messageInput);
        messageInput.clear();
        messageInput.sendKeys(message);
        return this;
    }

    public TransactionPage clickSend() {
        waitForClickability(sendButton);
        sendButton.click();
        return this;
    }

    public String getTopTransactionText() {
        waitForVisibility(firstTxnRow);
        return firstTxnRow.getText();
    }

    public void transfer(String receiverUserId, String amount, String message) {
        this.setReceiver(receiverUserId)
                .setAmount(amount)
                .setMessage(message)
                .clickSend();
    }

    public boolean isSendButtonDisabled() {
        waitForVisibility(sendButton);
        return !sendButton.isEnabled();
    }

    public boolean isErrorVisible() {
        waitForVisibility(errorBanner);
        return errorBanner.isDisplayed();
    }

    public String getErrorMsg() {
        waitForVisibility(errorBanner);
        return errorBanner.getText();
    }

    public boolean isSuccessVisible() {
        waitForVisibility(successBanner);
        return successBanner.isDisplayed();
    }

    public String getSuccessMsg() {
        waitForVisibility(successBanner);
        return successBanner.getText();
    }

    public void navigate(String route) {
        super.navigate(route);
    }
}