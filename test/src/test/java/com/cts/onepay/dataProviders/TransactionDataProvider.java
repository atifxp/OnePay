package com.cts.onepay.dataProviders;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class TransactionDataProvider {
    @DataProvider(name = "transferData")
    public Object[][] getTransferData() {
        return ExcelUtils.getSheetData(ConfigReader.get("sheet.data.transfer"));
    }
}