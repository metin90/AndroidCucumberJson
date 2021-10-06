package BaseFiles;

import GeneralFiles.Data;
import GeneralFiles.KeepData;
import GeneralFiles.OsCheck;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LaunchAppiumServerWithNodeJS extends AndroidConstants {

    private static AppiumServiceBuilder service;
    private static AppiumDriverLocalService localService = null;

    public static String getJSPath() throws IOException, InterruptedException {

        String jsPaths = null;
        String actualJSPath = null;

        String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.contains("Win")) {

            String whereAppium = "where" + " " + "appium";

            Process p = Runtime.getRuntime().exec(whereAppium);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((jsPaths = stdInput.readLine()) != null) {
                actualJSPath = jsPaths.replace("appium", "node_modules\\appium\\build\\lib\\main.js");
                break;
            }

            p.waitFor();

            p.destroy();

            if (actualJSPath == null) {
                System.exit(0);
            }

        } else {
            actualJSPath = "/usr/local/lib/node_modules/appium/build/lib/main.js";
        }
        return actualJSPath;
    }

    public static void startAppium(String ipAddress, int usingPort){

       try {
           String nodeJSPath="";
           if (OsCheck.getOperatingSystemType().equals("Windows")){
               nodeJSPath=Data.path_NodeJS_Win;
           }else{
               nodeJSPath=Data.path_NodeJS_mac;
           }

           System.out.println("Trying to start appium Server!");

           if (!TestBase.USEFREEPORT_VIA_NODEJS){
               Map<String, String> env = new HashMap<>(System.getenv());
               service=new AppiumServiceBuilder().usingDriverExecutable(new File(nodeJSPath))
                       .withAppiumJS(new File(getJSPath()))
                       .withIPAddress(ipAddress).usingPort(usingPort)
                       .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                       .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                       .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                       .withEnvironment(env)
                       //.withLogFile(new File("target/appium.log"))
                       .withStartUpTimeOut(120, TimeUnit.SECONDS);
               BasicConfigurator.configure();
               service.build().start();

           }else{
               Map<String, String> env = new HashMap<>(System.getenv());
               localService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                       .usingAnyFreePort()
                       .withAppiumJS(new File(getJSPath()))
                       .usingDriverExecutable(new File(nodeJSPath))
                       .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                       .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                       .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                       .withEnvironment(env)
                       //.withLogFile(new File("target/appium.log"))
                       .withStartUpTimeOut(120, TimeUnit.SECONDS));

               System.out.println("New Appium service: " + localService.getUrl());
               KeepData.setUrl(String.valueOf(localService.getUrl()));
               localService.start();

           }
           System.out.println("Appium Server is at your service! IP Address: "+ipAddress+", Port: "+usingPort);

       }catch (Exception e){
           System.out.println("Service got error while starting: "+e.getMessage());
       }

    }

    public static void stopAppium(){

        System.out.println("Trying to stop Appium service!");

        try {
            if (!TestBase.USEFREEPORT_VIA_NODEJS){
                service.build().stop();
            }else{
               // localService.stop();
            }

            System.out.println("Appium server is now shut down!");

        }catch (Exception e){

            System.out.println("Appium server is already stopped!");
        }

    }





}
