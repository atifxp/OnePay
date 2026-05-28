package com.cts.onepay.base;

import com.cts.onepay.utils.ConfigReader;
import com.cts.onepay.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = DriverManager.getDriver(ConfigReader.get("browser"),ConfigReader.getBoolean("headless"));
        driver.manage().window().maximize();
        //driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if(driver != null)
            driver.quit();
    }
}
