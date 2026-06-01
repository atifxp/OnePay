package com.cts.onepay.tests;

import com.cts.onepay.base.BaseTest;
import com.cts.onepay.pages.DashboardPage;
import com.cts.onepay.pages.LoginPage;
import com.cts.onepay.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.cts.onepay.dataProviders.LoginDataProvider;

public class LoginTest extends BaseTest {


    @Test(
            dataProvider = "loginData",
            dataProviderClass = LoginDataProvider.class,
            description = "Login Tests covering TCs - TC_LOGIN_01,TC_LOGIN_02,TC_LOGIN_03"
    )
    public void loginScenarios(
            String TC_ID,
            String phoneNum,
            String password,
            String expectedResult
    ) throws InterruptedException {
        String loginUrl = ConfigReader.get("route.login");

        //navigate to the page
        LoginPage page = new LoginPage(driver);
        page.navigate(loginUrl);


        switch(expectedResult.toLowerCase()){
            case "success":
                //entering credentials
                page.validLoginAs(phoneNum,password);
                Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),"Error in " + TC_ID);
                DashboardPage dpage= new DashboardPage(driver);
                dpage.clickLogout();
                break;
            case "failure":
                page.loginAs(phoneNum,password);
                Assert.assertTrue(page.isErrorBannerVisible(),"Error in " + TC_ID);
                Assert.assertEquals(page.getErrorMsg(), "Invalid phone or password","Error in " + TC_ID);
                break;
            case "validation":
                page.setPhone(phoneNum)
                    .setPassword(password);
                Assert.assertTrue(page.isLoginButtonDisabled(),"Error in " + TC_ID);
                break;
            default: throw new RuntimeException("Illegal Test Case Encountered in Login with id: " + TC_ID);
        }
    }

}
