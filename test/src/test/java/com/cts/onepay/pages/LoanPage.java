package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LoanPage extends BasePage {

    public LoanPage(WebDriver driver) {
        super(driver);
    }


    //Loan Application Page

    @FindBy(name = "loanType")
    WebElement loanTypeSel;
    @FindBy(name = "loanAmount")
    WebElement amountInput;
    @FindBy(name = "tenureMonth")
    WebElement tenureInput;
    @FindBy(name = "annualIncome")
    WebElement incomeInput;
    @FindBy(name = "purpose")
    WebElement purposeInput;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitBtn;
    @FindBy(id = "errorMsg")
    WebElement errorBanner;

    //Loan Details Page

    @FindBy(xpath = "//a[@href='/loans']")
    WebElement backToLoansLink;
    @FindBy(id = "loanStatus")
    WebElement loanStatus;

    public void applyForLoan(String loanType, String amount, String tenure, String income, String purpose) {
        this.setLoanType(loanType)
                .setAmount(amount)
                .setTenure(tenure)
                .setAnnualIncome(income)
                .setMessage(purpose)
                .submitApplication();
    }

    public LoanPage setLoanType(String loanType) {
        waitForClickability(loanTypeSel);
        new Select(loanTypeSel).selectByValue(loanType);
        return this;
    }

    public LoanPage setAmount(String amount) {
        waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    public LoanPage setTenure(String tenure) {
        waitForVisibility(tenureInput);
        tenureInput.clear();
        tenureInput.sendKeys(tenure);
        return this;
    }

    public LoanPage setAnnualIncome(String income) {
        waitForVisibility(incomeInput);
        incomeInput.clear();
        incomeInput.sendKeys(income);
        return this;
    }

    public LoanPage setMessage(String message) {
        waitForVisibility(purposeInput);
        purposeInput.clear();
        purposeInput.sendKeys(message);
        return this;
    }

    public LoanPage submitApplication() {
        waitForClickability(submitBtn);
        submitBtn.click();
        return this;
    }

    public void navigate(String route) {
        super.navigate(route);
    }

    public boolean isSubmitButtonDisabled(){
        scrollIntoView(submitBtn);
        waitForVisibility(submitBtn);
        return !submitBtn.isEnabled();
    }

    public boolean isErrorBannerVisible(){
        waitForVisibility(errorBanner);
        return errorBanner.isDisplayed();
    }

    public String getErrorMsg(){
        waitForVisibility(errorBanner);
        return errorBanner.getText();
    }

    public String getLoanStatus(){
        waitForVisibility(loanStatus);
        return loanStatus.getText();
    }


}
