package GeneralFiles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;

import java.io.FileReader;

public class JsonMethods {

    private static JsonObject jsonObject=null;

    private static JsonObject getJsonObject() {
        return jsonObject;
    }

    public static void setJsonObject(String jsonFileName) {
        try {
            JsonParser jsonParser= new JsonParser();
            FileReader fileReader= new FileReader(Data.jsonFilesPath+jsonFileName);
            Object object= jsonParser.parse(fileReader);
            JsonMethods.jsonObject = (JsonObject) object;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getJsonData(String data){
        String jsonData=null;
      try {
          return String.valueOf(getJsonObject().get(data));

      }catch (Exception e){
          e.printStackTrace();
      }
      return jsonData;
    }

    public static By getLocator(String jsonParameterName){
        By locator=null;
        try {
            String array[]=getJsonData(jsonParameterName).split(";");
            String data=array[1];
            if(array[0].equalsIgnoreCase("id")){
                locator=By.id(data);
            }else if(array[0].equalsIgnoreCase("className")){
                locator=By.className(data);
            }else if(array[0].equalsIgnoreCase("xpath")){
                locator=By.xpath(data);
            }else if(array[0].equalsIgnoreCase("cssSelector")){
                locator=By.cssSelector(data);
            }else if(array[0].equalsIgnoreCase("linkText")){
                locator=By.linkText(data);
            }else if(array[0].equalsIgnoreCase("partialLinkText")){
                locator=By.partialLinkText(data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return locator;
    }



}
