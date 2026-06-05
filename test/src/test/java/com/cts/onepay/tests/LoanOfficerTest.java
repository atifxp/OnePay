package com.cts.onepay.tests;

import com.cts.onepay.base.BaseTest;
import com.cts.onepay.dataProviders.LoanOfficerDataProvider;
import com.cts.onepay.pages.LoanDecisionPage;
import com.cts.onepay.pages.LoginPage;
import com.cts.onepay.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoanOfficerTest extends BaseTest {
    LoanDecisionPage decisionPage;
    LoginPage loginPage;

    @BeforeClass(groups = {"sanity","regression"})
    public void loginAsLoanOfficer(){
        decisionPage= new LoanDecisionPage(driver);
        loginPage= new LoginPage(driver);
        loginPage.navigate(ConfigReader.get("route.login"));
        loginPage.validLoginAs(
                ConfigReader.get("officer.phone"),
                ConfigReader.get("officer.password")
        );
    }

    @BeforeMethod(groups = {"sanity","regression"})
    public void navigateToLoanPage(){
        decisionPage.navigate(ConfigReader.get("route.admin.loans"));
    }

    @Test(
            dataProvider = "loanOfficerData",
            dataProviderClass = LoanOfficerDataProvider.class,
            groups = {"sanity","regression"},
            description = "Loan Tests covering TCs - TC01, TC02"
    )
    public void decideLoanApplication(String TC_ID, String action, String expectedResult){
        Assert.assertTrue(decisionPage.decideLoan(action), "Decision action failed");
    }
}
