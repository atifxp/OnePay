package com.cts.onepay.dataProviders;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class LoanApplyDataProvider {

    @DataProvider(name = "loanApplyData")
    public Object[][] loanData(){
        return ExcelUtils.getSheetData(ConfigReader.get("sheet.data.loan.apply"));
    }
}
