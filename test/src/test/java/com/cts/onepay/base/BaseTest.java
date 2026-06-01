package com.cts.onepay.base;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.DriverManager;
import com.cts.onepay.utils.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


public class BaseTest {

    protected static WebDriver driver;

    @BeforeSuite
    public void beforeSuite(){}


    @BeforeClass
    public void setUp(){
        driver = DriverManager.getDriver(ConfigReader.get("browser"),ConfigReader.getBoolean("headless"));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void afterSuite(){
        //close excel
        ExcelUtils.close();
        System.out.println("Excel closed");
    }
}
