package BaseFiles;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Environments {

    public static AppiumDriver prepareDriver(DesiredCapabilities dc, String platformName, String deviceName, String platformVersion, String udID, String ipAddress, String port) {
        BaseEnvironment baseEnvironment = Environment.prepareBrowser();
        AppiumDriver driver;
        if(TestBase.TRIGGER_APPIUMSERVER_MANUALLY){
            driver = baseEnvironment.runWithAppiumServer(dc,ipAddress,port);
        }
        else if (TestBase.ACTIVATE_APPIUMSERVER_WITH_NODEJS) {
            driver = baseEnvironment.runWithNodeJs(platformName,deviceName,platformVersion,udID,ipAddress,port);
        } else {
            driver = baseEnvironment.runWithAppiumServer(dc,ipAddress,port);
        }

        return driver;
    }


}
