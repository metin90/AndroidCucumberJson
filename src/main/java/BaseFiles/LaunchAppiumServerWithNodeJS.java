package BaseFiles;

import GeneralFiles.Data;
import GeneralFiles.OsCheck;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class LaunchAppiumServerWithNodeJS extends AndroidConstants {

    private static AppiumServiceBuilder service;

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

           service=new AppiumServiceBuilder().usingDriverExecutable(new File(nodeJSPath))
                   .withAppiumJS(new File(getJSPath()))
           .withIPAddress(ipAddress).usingPort(usingPort);

           BasicConfigurator.configure();

           service.build().start();

           System.out.println("Appium Server is at your service!");

       }catch (Exception e){
           System.out.println("Service got error while starting: "+e.getMessage());
       }

    }

    public static void stopAppium(){

        System.out.println("Trying to stop Appium service!");

        try {

            service.build().stop();

            System.out.println("Appium server is now shut down!");

        }catch (Exception e){

            System.out.println("Appium server is already stopped!");
        }

    }





}
