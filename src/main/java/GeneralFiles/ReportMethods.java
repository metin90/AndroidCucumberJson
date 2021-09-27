package GeneralFiles;

import BaseFiles.DriverManager;
import BaseFiles.KeepData;
import BaseFiles.TestBase;
import GeneralFiles.TestLinkIntegration;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import testlink.api.java.client.TestLinkAPIResults;
import utils.ExtentReports.ExtentTestManager;



public class ReportMethods {

    private String getMessage(String testCaseName,String description){
        // Jira & TesLink Integration
        String getJiraNumber= KeepData.getJiraNumber(testCaseName);
        if (!getJiraNumber.equals("")){
            description+=" Jira Link: "+TestBase.JIRALINK+getJiraNumber;
        }
        return description;
    }

    public void Report_Pass(String testCaseName,String description){
        description=getMessage(testCaseName,description);
        //TestLinkIntegration.updateResults(testCaseName,description,TestLinkAPIResults.TEST_PASSED);
        //testInfo.log(Status.PASS,description);
        ExtentTestManager.getTest().log(LogStatus.PASS,description);
        Reporter.log(description);
    }

    public  void Report_Info(String description){
        //testInfo.log(Status.INFO,description);
        ExtentTestManager.getTest().log(LogStatus.INFO,description);
        Reporter.log(description);
    }

//    public  void Report_Skip(String description){
//        testInfo.log(Status.SKIP,description);
//        Reporter.log(description);
//    }
//
//    public  void Report_Warning(String description){
//
//        testInfo.log(Status.WARNING,description);
//        Reporter.log(description);
//    }

    public  void Report_Fail( String testCaseName,String description){

        description=getMessage(testCaseName,description);

        //TestLinkIntegration.updateResults(testCaseName,description,TestLinkAPIResults.TEST_FAILED);
        //testInfo.log(Status.FAIL,description,TakeScreenShotForReport());
        //Get driver from BaseTest and assign to local webDriver variable.

        WebDriver webDriver = DriverManager.getDriver();
        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);

        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.INFO,description);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Test Failed",
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

        Assert.fail(description);
    }

}
