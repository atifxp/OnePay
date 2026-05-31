package com.cts.onepay.tests;

import com.cts.onepay.base.BaseTest;
import com.cts.onepay.dataProviders.TransactionDataProvider;
import com.cts.onepay.pages.LoginPage;
import com.cts.onepay.pages.TransactionPage;
import com.cts.onepay.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTest extends BaseTest {

    @Test(
            dataProvider = "transferData",
            dataProviderClass = TransactionDataProvider.class,
            description = "Transfer Tests covering TCs - TC_TXN_01, TC_TXN_02, TC_TXN_03"
    )
    public void transferScenarios(
            String TC_ID,
            String receiverUserId,
            String amount,
            String message,
            String expectedResult
    ) throws InterruptedException {
        // Login as verified customer
        LoginPage login = new LoginPage(driver);
        login.navigate(ConfigReader.get("route.login"));
        login.validLoginAs(
                ConfigReader.get("customer.phone"),
                ConfigReader.get("customer.password")
        );

        // Go to Transaction page and initiate txn
        TransactionPage page = new TransactionPage(driver);
        page.navigate(ConfigReader.get("route.transactions"));
        page.openTransferForm();

        switch (expectedResult.toLowerCase()) {
            case "success":
                page.transfer(receiverUserId, amount, message);
                Assert.assertTrue(page.isSuccessVisible(), "Error in " + TC_ID);
                Assert.assertTrue(page.getSuccessMsg().contains("sent successfully"),
                        "Error in " + TC_ID);
                String top = page.getTopTransactionText();
                Assert.assertTrue(top.contains(message), "Newest row isn't this transfer - " + TC_ID);
                Assert.assertTrue(top.contains("DEBIT"),     "Error in " + TC_ID);
                Assert.assertTrue(top.contains("COMPLETED"), "Error in " + TC_ID);
                break;
            case "failure":
                page.transfer(receiverUserId, amount, message);
                Assert.assertTrue(page.isErrorVisible(), "Error in " + TC_ID);
                // Backend IllegalArgumentException is unmapped -> 500 -> UI fallback text
                Assert.assertEquals(page.getErrorMsg(),
                        "Transfer failed. Please try again.", "Error in " + TC_ID);
                break;
            case "validation":
                page.setReceiver(receiverUserId)
                        .setAmount(amount)
                        .setMessage(message);
                Assert.assertTrue(page.isSendButtonDisabled(), "Error in " + TC_ID);
                break;
            default:
                throw new RuntimeException("Illegal Test Case in Transaction with id: " + TC_ID);
        }
    }
}