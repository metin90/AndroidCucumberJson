package BaseFiles;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface BaseEnvironment {

    AppiumDriver runWithNodeJs(String platformName, String deviceName, String platformVersion, String udID, String ipAddress, String port);
    AppiumDriver runWithAppiumServer(DesiredCapabilities dc, String ipAddress, String port);
}
