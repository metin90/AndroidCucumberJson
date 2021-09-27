package BaseFiles;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;


public class LaunchAppiumServer {

    private static AppiumDriverLocalService service = null;

    public static void startAppiumServer(DesiredCapabilities cap,String ipAddress,String port) {
        try {
            //Build the Appium service
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress(ipAddress);
            builder.usingPort(Integer.parseInt(port));
            builder.withCapabilities(cap);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

            //Start the server with the builder
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            System.out.println("Appium Server is at your service! IP Address: "+ipAddress+", Port: "+port);

        }catch (Exception e){
            System.out.println("Service got error while starting: "+e.getMessage());
        }
    }

    public static void stopAppiumServer() {
        service.stop();
    }
}
