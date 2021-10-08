package GeneralFiles;

import BaseFiles.TestBase;
import io.appium.java_client.MobileBy;
import io.cucumber.messages.internal.com.google.gson.JsonArray;
import io.cucumber.messages.internal.com.google.gson.JsonObject;
import io.cucumber.messages.internal.com.google.gson.JsonParser;
import org.openqa.selenium.By;

import java.io.FileReader;

public class JsonMethods {

    private static JsonObject jsonObject=null;

    private static JsonObject getJsonObject() {
        return jsonObject;
    }

    private static void setJsonObject(String jsonFileName) {
        try {
            JsonParser jsonParser= new JsonParser();
            FileReader fileReader= new FileReader(Data.jsonFilesPath+jsonFileName);
            Object object= jsonParser.parse(fileReader);
            JsonObject jsonObject= (JsonObject) object;

            JsonArray array= (JsonArray) jsonObject.get("testData");
            for (int i = 0; i <array.size() ; i++) {
                JsonObject testData= (JsonObject) array.get(i);
                String platformName= String.valueOf(testData.get("platformName"));
                if (platformName.contains(TestBase.PLATFORMNAME)){
                    JsonMethods.jsonObject =testData;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getJsonData(String jsonFileNameAndJsonParameterName){
        String jsonData=null;
        String jsonFileName=null;
        String jsonParameterName=null;
        try {
            String array[]=jsonFileNameAndJsonParameterName.split("@");
            jsonFileName=array[0]+".json";
            jsonParameterName=array[1];
          setJsonObject(jsonFileName);
          return String.valueOf(getJsonObject().get(jsonParameterName));

      }catch (Exception e){
          e.printStackTrace();
      }
      return jsonData;
    }

    public static By getLocator(String jsonFileNameAndJsonParameterName){
        By locator=null;
        try {
            jsonFileNameAndJsonParameterName=jsonFileNameAndJsonParameterName.trim();
            String array[]=getJsonData(jsonFileNameAndJsonParameterName).split(";");
            String data=array[1];
            array[0]=array[0].replaceAll("\"","");
            data=data.substring(0,data.length() - 1);

            if(array[0].equalsIgnoreCase("id")){
                locator= MobileBy.id(data);
            }else if(array[0].equalsIgnoreCase("className")){
                locator=MobileBy.className(data);
            }else if(array[0].equalsIgnoreCase("xpath")){
                locator=MobileBy.xpath(data);
            }else if(array[0].equalsIgnoreCase("cssSelector")){
                locator=MobileBy.cssSelector(data);
            }else if(array[0].equalsIgnoreCase("linkText")){
                locator=MobileBy.linkText(data);
            }else if(array[0].equalsIgnoreCase("partialLinkText")){
                locator=MobileBy.partialLinkText(data);
            }else if(array[0].equalsIgnoreCase("AccessibilityId")){
                locator=MobileBy.AccessibilityId(data);
            }else if(array[0].equalsIgnoreCase("iOSNsPredicateString") || array[0].contains("predicate")){
                locator=MobileBy.iOSNsPredicateString(data);
            }else if(array[0].equalsIgnoreCase("iOSClassChain") || array[0].contains("chain")){
                locator=MobileBy.iOSClassChain(data);
            }else if(array[0].equalsIgnoreCase("AndroidUIAutomator")) {
                locator = MobileBy.AndroidUIAutomator(data);
            }

            }catch (Exception e){
            e.printStackTrace();
        }
        return locator;
    }



}
