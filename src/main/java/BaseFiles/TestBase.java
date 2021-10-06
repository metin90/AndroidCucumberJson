package BaseFiles;


import GeneralFiles.PropertiesFileReader;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.*;
import org.testng.annotations.*;

import utils.ExtentReports.ExtentTestManager;


import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;


public class TestBase {

    public static int RETRY=0;
    public static String JIRALINK=null;
    public static boolean ACTIVATE_APPIUMSERVER_WITH_NODEJS=false;
    public static String PLATFORMNAME=null;
    public static boolean IOSREALDEVICE=false;
    public static boolean USEFREEPORT_VIA_NODEJS=false;
    public static String UDID=null;
    public static boolean TRIGGER_APPIUMSERVER_MANUALLY=false;


    public static AppiumDriver driver;

    public void initializeConfig(String platformName,String udID){

        try {

            PropertiesFileReader obj= new PropertiesFileReader();
            Properties properties= obj.getProperty();

            RETRY=Integer.parseInt((properties.getProperty("reTry")).trim());
            JIRALINK=properties.getProperty("jiraLink")+"/";
            PLATFORMNAME=platformName;
            UDID=udID;

            if (properties.getProperty("activate_AppiumServer_WithNodeJS").toLowerCase().equalsIgnoreCase("true")){
                ACTIVATE_APPIUMSERVER_WITH_NODEJS=true;
            }else{
                ACTIVATE_APPIUMSERVER_WITH_NODEJS=false;
            }

            if (properties.getProperty("iosRealDevice").toLowerCase().equalsIgnoreCase("true")){
                IOSREALDEVICE=true;
            }else{
                IOSREALDEVICE=false;
            }
            if (properties.getProperty("useFreePort_ViaNodeJS").toLowerCase().equalsIgnoreCase("true")){
                USEFREEPORT_VIA_NODEJS=true;
                ACTIVATE_APPIUMSERVER_WITH_NODEJS=true;
            }

            if (properties.getProperty("trigger_AppiumServer_Manually").toLowerCase().equalsIgnoreCase("true")){
                TRIGGER_APPIUMSERVER_MANUALLY=true;
            }

        }catch (Exception e){
            Assert.assertTrue(false,"Please check main/resouces/config.properties file !");
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context){

        Iterator var8 = context.getSuite().getAllMethods().iterator();

        while(var8.hasNext()) {
            ITestNGMethod method = (ITestNGMethod)var8.next();
            method.setRetryAnalyzer(new ReTryTestCase());
        }
    }



    @Parameters({"platformName_","deviceName_","platformVersion_","UDID_", "IPAddress_","Port_" })
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method,ITestResult arg1,String platformName_,String deviceName_, String platformVersion_, String UDID_, String IPAddress_,String Port_){

        initializeConfig(platformName_,UDID_);
        ExtentTestManager.startTest(method.getName(),arg1.getMethod().getDescription());
        DesiredCapabilities dc =null;


        try {
            if (!ACTIVATE_APPIUMSERVER_WITH_NODEJS || TRIGGER_APPIUMSERVER_MANUALLY){
                if (PLATFORMNAME.equalsIgnoreCase("Android")) {
                    dc=Android.getCapabilities(platformName_, deviceName_, UDID_, IPAddress_, Port_);
                }else{
                    dc= IOS.getCapabilities(platformName_, deviceName_,platformVersion_, UDID_, IPAddress_, Port_);
                }
                if (!TRIGGER_APPIUMSERVER_MANUALLY){
                    LaunchAppiumServer.startAppiumServer(dc,IPAddress_,Port_);
                }
            }else{
                LaunchAppiumServerWithNodeJS.startAppium(IPAddress_,Integer.parseInt(Port_));
            }
            DriverManager.setDriver(Environments.prepareDriver(dc,platformName_,deviceName_,platformVersion_,UDID_,IPAddress_,Port_));
            driver=DriverManager.getDriver();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result)  {

        if (DriverManager.getDriver() != null && !DriverManager.getDriver().toString().contains("null")) {
            DriverManager.getDriver().quit();

            if (!TRIGGER_APPIUMSERVER_MANUALLY){
                if (!ACTIVATE_APPIUMSERVER_WITH_NODEJS){
                    LaunchAppiumServer.stopAppiumServer();
                }else{
                    LaunchAppiumServerWithNodeJS.stopAppium();
                }
            }
        }

        IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer();
        if (retry != null) {
            if (result.getStatus() == 1) {
                result.getTestContext().getSkippedTests().removeResult(result.getMethod());
                result.getTestContext().getFailedTests().removeResult(result.getMethod());
            }

        }

    }






}
