package BaseFiles;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface BaseEnvironment {

    MobileDriver runWithNodeJs(String platformName, String deviceName, String platformVersion, String udID, String ipAddress, String port);
    MobileDriver runWithAppiumServer(DesiredCapabilities dc, String ipAddress, String port);
}
