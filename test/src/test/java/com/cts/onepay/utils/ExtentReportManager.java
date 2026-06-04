package com.cts.onepay.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager implements ITestListener {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extentReport;
    public static ExtentTest testLogger;

    @Override
    public void onStart(ITestContext context) {
        if(extentReport == null){
            String fileName = "myExtentReport_"+ DateTimeFormatter.ofPattern("YYYYMMdd_HHmm").format(LocalDateTime.now()) + ".html";
            sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") +"/test-output/reports/"+ fileName);


            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Functional Testing");
            sparkReporter.config().setTheme(Theme.DARK);

            extentReport = new ExtentReports();
            extentReport.attachReporter(sparkReporter);

            extentReport.setSystemInfo("Computer Name", ConfigReader.get("machine.host"));
            extentReport.setSystemInfo("Environment",ConfigReader.get("environment"));
            extentReport.setSystemInfo("Tester Name",ConfigReader.get("tester.name"));
            extentReport.setSystemInfo("OS",ConfigReader.get("os.name"));
            extentReport.setSystemInfo("Browser Name",ConfigReader.get("browser"));
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        testLogger = extentReport.createTest(result.getName());

        //logging test data in report
        Object[] params = result.getParameters();
        if(params !=null && params.length > 0){
            StringBuilder info = new StringBuilder("<b>Test Data Used:</b><br>");
            for (Object param: params){
                info.append("🔸"+param.toString()+ "<br>");
            }
            testLogger.info(info.toString());
        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
         //create a new entry in the report
        testLogger.log(Status.PASS,"Test case PASSED is: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testLogger = extentReport.createTest(result.getName()); //create a new entry in the report
        testLogger.log(Status.FAIL,"Test case FAILED is: " + result.getName());

        WebDriver driver = DriverManager.getDriver(ConfigReader.get("browser"),ConfigReader.getBoolean("headless"));;
        if(driver != null){
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                    driver,result.getMethod().getMethodName()
            );
            testLogger.addScreenCaptureFromPath(screenshotPath,"Failure Screenshot");
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testLogger = extentReport.createTest(result.getName()); //create a new entry in the report
        testLogger.log(Status.SKIP,"Test case SKIPPED is: " + result.getName());
    }


    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();   //writes the test information in the report
    }

}
