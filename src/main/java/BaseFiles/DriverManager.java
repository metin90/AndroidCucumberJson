package BaseFiles;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal();

    public DriverManager() {
    }

    public static synchronized MobileDriver getDriver() {
        return (MobileDriver)driverThread.get();
    }


    public static synchronized void setDriver(MobileDriver driver) {
        driverThread.set(driver);
    }

}