package BaseFiles;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal();

    public DriverManager() {
    }

    public static synchronized AppiumDriver getDriver() {
        return (AppiumDriver) driverThread.get();
    }


    public static synchronized void setDriver(AppiumDriver driver) {
        driverThread.set(driver);
    }

}