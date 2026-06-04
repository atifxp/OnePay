package com.cts.onepay.pages;

import com.cts.onepay.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LoanDecisionPage extends BasePage {
    public LoanDecisionPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//div[@id='loan_box'][.//span[contains(text(),'SUBMITTED')]]")
    WebElement loanBox;

    @FindBy(xpath = "//div[@id='loan_box'][.//span[contains(text(),'SUBMITTED')]]//button[normalize-space()='Decide']")
    WebElement btnDecide;

    @FindBy(xpath = "//div[@id='loan_box'][.//span[contains(text(),'SUBMITTED')]]//select[@id='loan_select']")
    WebElement dropdownLoan;

    @FindBy(xpath = "//div[@id='decision_result']")
    WebElement decisionResult;

    @FindBy(xpath = "//div[@id='decision_error']")
    WebElement decisionError;

    public boolean decideLoan(String result){
        if(!waitForVisibility(loanBox).getText().contains("SUBMITTED")) return false;
        Select select= new Select(dropdownLoan);
        select.selectByVisibleText(capitalizeFirstLetter(result));
        waitForClickability(btnDecide).click();

        try {
            WebElement resultEl = waitForVisibility(decisionResult);
            if (resultEl.isDisplayed()) {
                return resultEl.getText().toUpperCase().contains(result.toUpperCase());
            }
        } catch (Exception e) {
            return waitForVisibility(decisionError).isDisplayed();
        }
        return false;
    }

    @Override
    public void navigate(String route) {
        super.navigate(route);
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        str=str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
