package BaseFiles;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LaunchAppiumServer {

    private static AppiumDriverLocalService service = null;

    public static void startAppiumServer(DesiredCapabilities cap,String ipAddress,String port) {
        try {
            //Build the Appium service
            Map<String, String> env = new HashMap<>(System.getenv());
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            builder.withIPAddress(ipAddress)
            .usingPort(Integer.parseInt(port))
            .withCapabilities(cap)
            .withArgument(GeneralServerFlag.RELAXED_SECURITY)
            .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
            .withArgument(GeneralServerFlag.LOG_LEVEL,"error")
                    .withEnvironment(env)
                    //.withLogFile(new File("target/appium.log"))
                    .withStartUpTimeOut(120, TimeUnit.SECONDS);

            //Start the server with the builder
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
           // System.out.println("Appium Service started at "+service.getUrl().toString());
            System.out.println("Appium Server is at your service! IP Address: "+ipAddress+", Port: "+port);

        }catch (Exception e){
            System.out.println("Service got error while starting: "+e.getMessage());
        }
    }

    public static void stopAppiumServer() {
        service.stop();
    }
}
