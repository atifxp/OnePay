package com.cts.onepay.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver(String browser, boolean headless){
        if(driver == null){

            switch (browser.toLowerCase()){
                case "chrome":
                    final Map<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("credentials_enable_service", false);
                    chromePrefs.put("profile.password_manager_enabled", false);
                    chromePrefs.put("profile.password_manager_leak_detection", false);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    if(headless)
                        chromeOptions.addArguments("--headless");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if(headless)
                        edgeOptions.addArguments("--headless");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                default: throw new IllegalArgumentException("Provided Browser not found");
            }
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver != null){
            driver.quit();
            driver = null; // to avoid driver to point towards the dead driver
        }
    }


}
