package com.cts.onepay.dataProviders;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData(){
        return ExcelUtils.getSheetData(ConfigReader.get("sheet.data.login"));
    }
}
