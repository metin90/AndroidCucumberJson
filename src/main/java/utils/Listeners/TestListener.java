package utils.Listeners;

import BaseFiles.DriverManager;
import BaseFiles.TestBase;
import GeneralFiles.BasePage;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReports.ExtentManager;
import utils.ExtentReports.ExtentTestManager;


public class TestListener extends TestBase implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        //ExtentReports log operation for passed tests.
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

        //Get driver from BaseTest and assign to local webDriver variable.
        Object testClass = iTestResult.getInstance();
        AppiumDriver webDriver = DriverManager.getDriver();

        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
            getScreenshotAs(OutputType.BASE64);

        // Jira & TesLink Integration
        String testName=iTestResult.getName();
        int getTestNumber= BasePage.getNumbersInString(testName);
//        String testCaseName=TestBase.TESTLINK_TESTCASE_PREFIX+"-"+getTestNumber;
//        String getJiraNumber= KeepData.getJiraNumber(testCaseName);
        String errorMessage="Test Failed.";

//        if (!getJiraNumber.equals("")){
//            errorMessage+=" Jira Link: "+TestBase.JIRALINK+getJiraNumber;
//        }

        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.INFO, errorMessage,
            ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));

        ExtentTestManager.getTest().log(LogStatus.FAIL, iTestResult.getThrowable());

        // TestLink integrated with ExtendReport
//        if (TestBase.TESTLINK_REPORT.equalsIgnoreCase("true")){
//        TestLinkIntegration.updateResults(testCaseName,errorMessage, TestLinkAPIResults.TEST_FAILED);
//        }

    }


    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        //ExtentReports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }


}
