package com.cts.onepay.tests;

import com.cts.onepay.base.BaseTest;
import com.cts.onepay.dataProviders.LoanApplyDataProvider;
import com.cts.onepay.pages.DashboardPage;
import com.cts.onepay.pages.LoanPage;
import com.cts.onepay.pages.LoginPage;
import com.cts.onepay.pages.VerificationPage;
import com.cts.onepay.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoanApplyTest extends BaseTest {

    LoginPage loginPage;

    @BeforeClass(description = "Login as a valid user", groups = {"sanity","regression"})
    public void loginAsValidUser(){
        //navigate to the login page
        loginPage = new LoginPage(driver);
        loginPage.navigate(ConfigReader.get("route.login"));
        loginPage.validLoginAs(ConfigReader.get("customer.phone"), ConfigReader.get("customer.password"));
    }


    @Test(
            dataProvider = "loanApplyData",
            dataProviderClass = LoanApplyDataProvider.class,
            groups = {"sanity","regression"},
            description = "Loan Tests covering TCs - TC_LOAN_01,TC_LOAN_02,TC_LOAN_03,TC_LOAN_04"
    )
    public void loanApplyScenarios(
            String TC_ID,
            String loanType,
            String amount,
            String tenure,
            String income,
            String purpose,
            String expectedResult
    ) throws InterruptedException {
        String loanUrl = ConfigReader.get("route.loan.apply");

        //navigate to apply page
        LoanPage loanPage = new LoanPage(driver);



        switch(expectedResult.toLowerCase()){
            case "success":
                loanPage.navigate(loanUrl);
                loanPage.applyForLoan(loanType,amount,tenure,income,purpose);
                Assert.assertEquals(loanPage.getLoanStatus(),"SUBMITTED","Error in " + TC_ID);
                break;
            case "failure":
                //logout from current session
                DashboardPage dashboardPage = new DashboardPage(driver);
                dashboardPage.clickLogout();
                //login as PENDING verification user
                loginPage.validLoginAs(ConfigReader.get("customer.pending.phone"), ConfigReader.get("customer.pending.password"));
                VerificationPage verificationPage = new VerificationPage(driver);
                loanPage.navigate(loanUrl);
                String verificationStatus = verificationPage.getVerificationStatusText();

                Assert.assertTrue(driver.getCurrentUrl().contains("/verification/status"),"Error in " + TC_ID);
                Assert.assertEquals(verificationStatus,"Pending Review","Error in " + TC_ID);
                break;
            case "validation":
                loanPage.navigate(loanUrl);
                loanPage.setLoanType(loanType)
                        .setAmount(amount)
                        .setTenure(tenure)
                        .setAnnualIncome(income)
                        .setMessage(purpose);
                Assert.assertTrue(loanPage.isSubmitButtonDisabled(),"Error in " + TC_ID);
                break;
            default: throw new RuntimeException("Illegal Test Case Encountered in Loan Apply with id: " + TC_ID);
        }
    }
}
