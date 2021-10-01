package GeneralFiles;

import java.io.*;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelMethods {

    private File createFileObject(String filePath,String fileName){
        return new File(filePath+"/"+fileName);
    }

    public Workbook createFile(String filePath,String fileName){
        Workbook workbook=null;
        try {
            //Create an object of File class to open xlsx file
            File file =  createFileObject(filePath,fileName);

            //Create an object of FileInputStream class to read excel file
            FileInputStream inputStream = new FileInputStream(file);

            //Find the file extension by splitting file name in substring  and getting only extension name
            String fileExtensionName = fileName.substring(fileName.indexOf("."));

            //Check condition if the file is xlsx file
            if(fileExtensionName.equals(".xlsx")){
                //If it is xlsx file then create object of XSSFWorkbook class
                workbook = new XSSFWorkbook(inputStream);
            }

            //Check condition if the file is xls file
            else if(fileExtensionName.equals(".xls")){
                //If it is xls file then create object of HSSFWorkbook class
                workbook = new HSSFWorkbook(inputStream);
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return workbook;
    }

    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

        Workbook workbook=createFile(filePath,fileName);

        //Read sheet inside the workbook by its name
        Sheet sheet = workbook.getSheet(sheetName);

        //Find number of rows in excel file
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        //Create a loop over all the rows of excel file to read it
        for (int i = 0; i < rowCount+1; i++) {
            Row row = sheet.getRow(i);
            //Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++) {
                //Print Excel data in console
                System.out.print(row.getCell(j).getStringCellValue()+"|| ");
            }
            System.out.println();
        }
    }

    public void writeExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{

        Workbook workbook=createFile(filePath,fileName);

        //Read excel sheet by sheet name
        Sheet sheet = workbook.getSheet(sheetName);

        //Get the current count of rows in excel file
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        //Get the first row from the sheet
        Row row = sheet.getRow(0);

        //Create a new row and append it at last of sheet
        Row newRow = sheet.createRow(rowCount+1);

        //Create a loop over the cell of newly created Row
        for(int j = 0; j < row.getLastCellNum(); j++){

            //Fill data in row
            Cell cell = newRow.createCell(j);
            cell.setCellValue(dataToWrite[j]);
        }

        //Close input stream
        FileInputStream fileInputStream= new FileInputStream(createFileObject(filePath,fileName));

        fileInputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file

        FileOutputStream outputStream = new FileOutputStream(createFileObject(filePath,fileName));

        //write data in the excel file
        workbook.write(outputStream);

        //close output stream
        outputStream.close();
    }

    public String readCellData(String fileName,int vRow, int vColumn) {
        String value=null;          //variable for storing the cell value
        Workbook wb=null;//initialize Workbook null
        try {
            wb=createFile(Data.filesPath,fileName);
            Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index
            Row row=sheet.getRow(vRow); //returns the logical row
            Cell cell=row.getCell(vColumn); //getting the cell representing the given column
            value=cell.getStringCellValue();    //getting cell value

        }catch (Exception e){
            value="";
        }
        return value; //returns the cell value
    }

    public ArrayList findData(String startDate,String endDate,String fileName,String nodeName,String vendor)
    {
        String v_date=null;
        String v_nodeName=null;
        String v_vendor=null;
        String v_json=null;
        ArrayList<String> jsonList =new ArrayList();
        Workbook wb=null;           //initialize Workbook null

        wb=createFile(Data.filesPath,fileName);

        Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index

        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
        Row row;
        Cell cell_Date,cell_NodeName,cell_Vendor,cell_Json;

        for (int i = 1; i <rowCount+1 ; i++) {
          row=sheet.getRow(i);
          cell_Date=row.getCell(0);
          cell_NodeName=row.getCell(1);
          cell_Vendor=row.getCell(2);

          v_date=cell_Date.getStringCellValue().replaceAll("/","");
          v_nodeName=cell_NodeName.getStringCellValue();
          v_vendor=cell_Vendor.getStringCellValue();

          startDate=startDate.replaceAll("/","");
          endDate=endDate.replaceAll("/","");

          if (Integer.parseInt(v_date)>=Integer.parseInt(startDate) && Integer.parseInt(v_date)<=Integer.parseInt(endDate)){
              if (v_nodeName.equals(nodeName) && v_vendor.equals(vendor)){
                  cell_Json=row.getCell(3);
                  v_json=cell_Json.getStringCellValue();
                  jsonList.add(v_json);
              }
          }
        }
        return jsonList;
    }

    public static void main(String[] args) throws IOException{

        //Create an object of ReadGuru99ExcelFile class

        ExcelMethods objExcelFile = new ExcelMethods();

        //Prepare the path of excel file

        String filePath = Data.filesPath;

        //Call read file method of the class to read data

        //objExcelFile.readExcel(filePath,"ParsingData.xlsx","JsonResults");

//        String[] valueToWrite = {"2020/04/18","Noida","ciscoXR","32423432432432"};
//        objExcelFile.writeExcel(filePath,"ParsingData.xlsx","JsonResults",valueToWrite);
//
//        ArrayList<String> jsonList=objExcelFile.findData("2020/01/20","2020/05/13","ParsingData.xlsx","Noida","ciscoXR");
//
//        for (int i = 0; i <jsonList.size() ; i++) {
//            System.out.println(jsonList.get(i));
//        }

        //System.out.println("Data: "+objExcelFile.readCellData("Jira_TestLink_Integration.xlsx",1,0));

        KeepData.setJiraNumber("Jira_TestLink_Integration.xlsx");
        System.out.println("Data: "+KeepData.getJiraNumber("Nap-20"));

    }





}
