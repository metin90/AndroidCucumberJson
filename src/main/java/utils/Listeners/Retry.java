package utils.Listeners;

import BaseFiles.DriverManager;
import GeneralFiles.BasePage;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.ExtentReports.ExtentTestManager;



public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static int maxTry =0; //Run the failed test 2 times

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (count < maxTry) {                            //Check if maxTry count is reached
                count++;                                     //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed and take base64Screenshot
                extendReportsFailOperations(iTestResult);    //ExtentReports fail operations
                return true;                                 //Tells TestNG to re-run the test
            }
        }
        else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        MobileDriver webDriver = DriverManager.getDriver();
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

        // Jira & TesLink Integration
        String testName=iTestResult.getName();
        int getTestNumber= BasePage.getNumbersInString(testName);
        //String testCaseName=TestBase.TESTLINK_TESTCASE_PREFIX+"-"+getTestNumber;
        //String getJiraNumber= KeepData.getJiraNumber(testCaseName);
        String errorMessage="Test Failed.";

//        if (!getJiraNumber.equals("")){
//            errorMessage+=" Jira Link: "+TestBase.JIRALINK+getJiraNumber;
//        }

        ExtentTestManager.getTest().log(LogStatus.INFO, errorMessage,
            ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        ExtentTestManager.getTest().log(LogStatus.FAIL, iTestResult.getThrowable());

        // TestLink integrated with ExtendReport
//        if (TestBase.TESTLINK_REPORT.equalsIgnoreCase("true")){
//            TestLinkIntegration.updateResults(testCaseName,errorMessage, TestLinkAPIResults.TEST_FAILED);
//        }

    }

}