package BaseFiles;


import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class IOS implements BaseEnvironment {

    public static DesiredCapabilities getCapabilities(String platformName,String deviceName,String platformVersion,String udID,String ipAddress,String port){
        DesiredCapabilities dc= new DesiredCapabilities();
        try {

            String reportDirectory = "reports";
            String reportFormat = "xml";

            //start android app

            dc.setCapability("reportDirectory", reportDirectory);
            dc.setCapability("reportFormat", reportFormat);
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");
            dc.setCapability("deviceName", deviceName);
            dc.setCapability(MobileCapabilityType.UDID, udID);


            dc.setCapability("unicodeKeyboard", true);
            dc.setCapability("resetKeyboard", true);

            if (TestBase.IOSREALDEVICE){
                dc.setCapability(MobileCapabilityType.APP, iOSConstants.appForRealDevive);
                dc.setCapability( "bundleId", iOSConstants.bundleID);
                dc.setCapability("xcodeOrgId", iOSConstants.xcodeOrgID);
                dc.setCapability("xcodeSigningId", iOSConstants.xcodeSigningID);
                dc.setCapability("useNewWDA",true);
                dc.setCapability("clearSystemFiles",true);
            }else{
                dc.setCapability(MobileCapabilityType.APP, iOSConstants.appForEmulator);
            }
            dc.setCapability("platformVersion",platformVersion);
            dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
            dc.setCapability("connectHardwareKeyboard",true);
            dc.setCapability("clearSystemFiles",true);
            dc.setCapability("showXcodeLog",true);
            dc.setCapability(MobileCapabilityType.NO_RESET, true);
            dc.setCapability(MobileCapabilityType.FULL_RESET, false);
            dc.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return dc;
    }

    @Override
    public MobileDriver runWithNodeJs(String platforName, String deviceName, String platformVersion, String udID, String ipAddress, String port) {

        IOSDriver driver = null;
        DesiredCapabilities dc =getCapabilities(platforName,deviceName,platformVersion,udID,ipAddress,port);
        try {
            driver= new IOSDriver(new URL("http://"+ipAddress+":"+port+"/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver==null){
            Assert.fail("Check Android Driver!");
        }
        return driver;
    }

    @Override
    public MobileDriver runWithAppiumServer(DesiredCapabilities dc, String ipAddress, String port) {

        IOSDriver driver = null;
        try {
            driver= new IOSDriver(new URL("http://"+ipAddress+":"+port+"/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (driver==null){
            Assert.fail("Check Android Driver!");
        }
        return driver;
    }
}
