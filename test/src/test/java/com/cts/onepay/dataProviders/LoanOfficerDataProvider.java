package com.cts.onepay.dataProviders;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class LoanOfficerDataProvider {
    @DataProvider(name = "loanOfficerData")
    public Object[][] getLoanOfficerData(){
        return ExcelUtils.getSheetData(ConfigReader.get("sheet.data.admin"));
    }
}
