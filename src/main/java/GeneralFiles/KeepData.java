package GeneralFiles;

import BaseFiles.TestBase;

import java.util.ArrayList;
import java.util.HashMap;

public class KeepData {

    private static String workOrderID=new String();
    private static String workOrderNumber=new String();
    private static String jiraNumber=new String();
    private static ArrayList<String> testLinkNumber_List=new ArrayList<>();
    private static ArrayList<String> jiraNumber_List=new ArrayList<>();
    private static String url=new String();
    private static HashMap<String,String> keepHashData=new HashMap<>();


    public static String getWorkOrderID() {
        return workOrderID;
    }

    public static void setWorkOrderID(String workOrderID) {
        KeepData.workOrderID = workOrderID;
    }

    public static String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public static void setWorkOrderNumber(String workOrderNumber) {
        KeepData.workOrderNumber = workOrderNumber;
    }

    public static String getJiraNumber(String testLinkNo) {

        int i=0;
        boolean dataExist=false;

        for (String data:testLinkNumber_List
             ) {
            if(testLinkNo.equalsIgnoreCase(data)){
                dataExist=true;
                break;
            }
            ++i;
        }

        if(dataExist){
            jiraNumber=jiraNumber_List.get(i);
        }else {
            jiraNumber="";
        }
        return jiraNumber;
    }

    public static void setJiraNumber(String fileName) {
      try {
          ExcelMethods excelMethods=new ExcelMethods();
          int i=1;
          String testLinkNo="",jiraNo="";

          testLinkNo=excelMethods.readCellData(fileName,i,0);
          jiraNo=excelMethods.readCellData(fileName,i,1);

          while (!testLinkNo.equals("")){
              KeepData.testLinkNumber_List.add(testLinkNo);
              KeepData.jiraNumber_List.add(jiraNo);
              ++i;
              testLinkNo=excelMethods.readCellData(fileName,i,0);
              jiraNo=excelMethods.readCellData(fileName,i,1);
          }

      }catch (Exception e){
          e.getStackTrace();
      }
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        KeepData.url = url;
    }

    public static String getKeepHashData(String parameter) {
        parameter=parameter+"-"+TestBase.UDID;
        return keepHashData.get(parameter);
    }

    public static void setKeepHashData(String parameter, String value) {
        parameter=parameter+"-"+TestBase.UDID;
        KeepData.keepHashData.put(parameter,value);
    }
}
