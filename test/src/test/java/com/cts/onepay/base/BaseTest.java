package com.cts.onepay.base;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.DriverManager;
import com.cts.onepay.utils.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class BaseTest {

    protected static WebDriver driver;

    @BeforeSuite
    public void beforeSuite(){}


    @BeforeMethod
    public void setUp(){
        driver = DriverManager.getDriver(ConfigReader.get("browser"),ConfigReader.getBoolean("headless"));
        driver.manage().window().maximize();
    }

    @AfterMethod
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
