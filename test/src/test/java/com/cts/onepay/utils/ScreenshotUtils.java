package com.cts.onepay.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = DateTimeFormatter.ofPattern("YYYYMMdd_HHmm").format(LocalDateTime.now());
        String fileName = testName + "_" + timestamp + ".png";
        String destPath = System.getProperty("user.dir") + "/test-output/screenshots/" + fileName;
        String relativePath = "../screenshots/" + fileName;
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return relativePath; // relative path returned for ExtentReport
    }

}
