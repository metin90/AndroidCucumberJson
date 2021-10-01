package BaseFiles;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import java.net.MalformedURLException;
import java.net.URL;

public class Android implements BaseEnvironment{

    public static DesiredCapabilities getCapabilities(String platformName,String deviceName,String udID,String ipAddress,String port){
        DesiredCapabilities dc= new DesiredCapabilities();
        try {

            String reportDirectory = "reports";
            String reportFormat = "xml";

            //start android app

            dc.setCapability("reportDirectory", reportDirectory);
            dc.setCapability("reportFormat", reportFormat);
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
            dc.setCapability("deviceName", deviceName);
            dc.setCapability(MobileCapabilityType.UDID, udID);


            dc.setCapability("unicodeKeyboard", true);
            dc.setCapability("resetKeyboard", true);
            dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");


            dc.setCapability(MobileCapabilityType.APP, AndroidConstants.app);
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, AndroidConstants.appPackage);
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, AndroidConstants.appActivity);
            dc.setCapability(MobileCapabilityType.NO_RESET, true);
            dc.setCapability(MobileCapabilityType.FULL_RESET, false);
            dc.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return dc;
    }

    @Override
    public  AppiumDriver runWithNodeJs(String platformName, String deviceName, String platformVersion, String udID, String ipAddress, String port) {

        AndroidDriver driver = null;
        DesiredCapabilities dc =getCapabilities(platformName,deviceName,udID,ipAddress,port);
        try {
            if (TestBase.USEFREEPORT_VIA_NODEJS){
                driver= new AndroidDriver(new URL(KeepData.getUrl()),dc);
            }else {
                driver = new AndroidDriver(new URL("http://" + ipAddress + ":" + port + "/wd/hub"), dc);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver==null){
            Assert.fail("Check Android Driver!");
        }
        return driver;
    }

    @Override
    public AppiumDriver runWithAppiumServer(DesiredCapabilities dc, String ipAddress, String port) {

        AndroidDriver driver = null;
        try {
            driver= new AndroidDriver(new URL("http://"+ipAddress+":"+port+"/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver==null){
            Assert.fail("Check Android Driver!");
        }
        return driver;
    }


}
