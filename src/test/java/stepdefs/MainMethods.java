package stepdefs;


import BaseFiles.TestBase;
import GeneralFiles.JsonMethods;
import GeneralFiles.KeepData;
import GeneralFiles.ReportMethods;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.cucumber.java.en.And;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static BaseFiles.DriverManager.getDriver;



public class MainMethods {

    public static ArrayList<String> windowList;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private  static final String NUMERIC_STRING="1234567890";

    public AppiumDriver driver= getDriver();

    ReportMethods report=new ReportMethods();

    public MobileElement waitUntilVisibleByLocator(By locator) {
        MobileElement element = null;

        try {
            Wait<AppiumDriver> wait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds(30L)).pollingEvery(Duration.ofSeconds(30L)).ignoring(NoSuchElementException.class);
            element = (MobileElement)wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        return element;
    }


    protected MobileElement waitUntilClickableByListOfElement(MobileElement MobileElement) {
        MobileElement element = null;

        try {
            Wait<AppiumDriver> wait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds(30L)).pollingEvery(Duration.ofSeconds(30L)).ignoring(NoSuchElementException.class);
            element = (MobileElement)wait.until(ExpectedConditions.elementToBeClickable(MobileElement));
        } catch (Exception e) {
           Assert.fail(e.getMessage());
        }

        return element;
    }


    protected MobileElement waitUntilPresenceOfElement(By locator) {
        MobileElement element = null;

        try {
            Wait<AppiumDriver> wait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds(30L)).pollingEvery(Duration.ofMillis(30L)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            element = (MobileElement)wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {

            //Assert.fail(e.getMessage());
        }

        return element;
    }

    public boolean checkElementIsExist(By locator){
        try {
            MobileElement element= (MobileElement) driver.findElement(locator);

            if (element.isDisplayed() || element.isEnabled()){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){

            return false;
        }
    }

    public boolean checkElementIsExist(MobileElement element){
        try {
            if (element.isDisplayed() || element.isEnabled()){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            return false;
        }
    }

    public boolean checkElementIsExist(By locator,By locator2){

        try {

            MobileElement element= (MobileElement) driver.findElement(locator);
            element=element.findElement(locator2);

            if (element.isDisplayed() || element.isEnabled()){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){

            return false;
        }
    }

    public boolean checkElementIsExist(MobileElement element,By locator){
        try {
            MobileElement element1=element.findElement(locator);
            if (element1.isDisplayed() || element1.isEnabled()){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public void checkPageIsReady() {

        JavascriptExecutor js = (JavascriptExecutor)driver;

        if (js.executeScript("return document.readyState").toString().equals("complete")){
            System.out.println("Page Is loaded.");
            return;
        }

        for (int i=0; i<25; i++){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {}
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete")){
                break;
            }
        }
    }


    public void clickElement(By locator) {
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        this.clickElement(element);
    }


    @And("^\"([^\"]*)\" tiklanir$")
    public void clickElement(String jsonParameterName) {
        By locator=JsonMethods.getLocator(jsonParameterName);
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        this.clickElement(element);
        report.Report_Info(jsonParameterName+" tiklanir");
    }

    @And("^\"([^\"]*)\" butonunu en fazla \"([^\"]*)\" sn bekle, var ise tikla")
    public void clickElementIfItExists(String jsonParameterName, String second) {
        By locator=JsonMethods.getLocator(jsonParameterName);
        boolean elementExist=waitElementWithThreadSleep(locator,Integer.valueOf(second));
        if (elementExist){
            clickElement(locator);
        }
    }

    @And("^\"([^\"]*)\" JS araciligi ile tiklanir$")
    public void clickElementViaJS(String jsonParameterName) {
       try {
           By locator=JsonMethods.getLocator(jsonParameterName);
           MobileElement element= (MobileElement) driver.findElement(locator);
           JavascriptExecutor executor = (JavascriptExecutor) driver;
           executor.executeScript("arguments[0].click();", element);
           report.Report_Info(jsonParameterName+" JS araciligi ile tiklanir");
       }catch (Exception e){
           Assert.fail(e.getMessage());
       }
    }

    protected void clickElement(MobileElement element) {
        this.waitUntilClickableByListOfElement(element).click();
    }

    public String getDataInListViaAttribute(By locator,String attributeName,int index){

        List<MobileElement> elements=null;
        String data=null;
        try {

            elements= driver.findElements(locator);
            data=elements.get(index).getAttribute(attributeName);

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return data;
    }

    public String getDataViaAttribute(MobileElement element,String attributeName){
        try {
            return  element.getAttribute(attributeName);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return null;
    }

    protected void sendKeysToElement(By locator, String text) {
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        element = this.waitUntilPresenceOfElement(locator);
        element.clear();
        element.sendKeys(new CharSequence[]{text});
    }

    @And("^\"([^\"]*)\" icerisine \"([^\"]*)\" parametre icindeki veri girilir$")
    public void sendKeysToElementUsingHashedData(String jsonParameterName, String dataParameter){
        By locator=JsonMethods.getLocator(jsonParameterName);
        String text= KeepData.getKeepHashData(dataParameter);
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        element = this.waitUntilPresenceOfElement(locator);
        element.clear();
        element.sendKeys(new CharSequence[]{text});
        report.Report_Info(jsonParameterName+" icerisine "+text+" verisi girilir");
    }

    @And("^\"([^\"]*)\" icerisine \"([^\"]*)\" verisi girilir$")
    public void sendKeysToElement(String jsonParameterName, String text) {
        By locator=JsonMethods.getLocator(jsonParameterName);
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        element = this.waitUntilPresenceOfElement(locator);
        element.clear();
        element.sendKeys(new CharSequence[]{text});
        report.Report_Info(jsonParameterName+" icerisine "+text+" verisi girilir");
    }

    @And("^\"([^\"]*)\" elementlerinden \"([^\"]*)\" siradakinin icerisine \"([^\"]*)\" verisi girilir$")
    protected void sendKeysToElementInList(String jsonParameterName,String index, String text) {
        By locator=JsonMethods.getLocator(jsonParameterName);
        MobileElement element = getMobileElementInList(locator,Integer.parseInt(index));
        element.clear();
        element.sendKeys(new CharSequence[]{text});
        report.Report_Info(jsonParameterName+" elementlerinden "
                +index+". siradakinin icerisine "+text+" verisi girilir");
    }

    protected void sendKeysToElement(By locator, Keys keys) {
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        element = this.waitUntilPresenceOfElement(locator);
        element.sendKeys(keys);
    }

    @And("^\"([^\"]*)\" icin ENTER tusuna tiklanir$")
    public void clickEnterButton(String jsonParameterName){
        By locator=JsonMethods.getLocator(jsonParameterName);
        Keys keys= Keys.ENTER;
        sendKeysToElement(locator,keys);
        report.Report_Info(jsonParameterName+" icin ENTER tusuna tiklanir");
    }

    @And("^\"([^\"]*)\" icin TAB tusuna tiklanir$")
    public void clickTabButton(String jsonParameterName){
        By locator=JsonMethods.getLocator(jsonParameterName);
        Keys keys= Keys.TAB;
        sendKeysToElement(locator,keys);
        report.Report_Info(jsonParameterName+" icin TAB tusuna tiklanir");
    }

    protected void sendKeysToElement(MobileElement element, String text) {
        element.clear();
        element.sendKeys(new CharSequence[]{text});
    }

    public void sendKeysToElementViaJS(By locator,String text){
        try {
            JavascriptExecutor js = (JavascriptExecutor)driver;
            MobileElement elem = (MobileElement) driver.findElement(locator);
            js.executeScript("arguments[0].value =`"+text+"`", elem);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    protected void sendKeysToElementWithoutClean(By locator, String text) {
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        element = this.waitUntilPresenceOfElement(locator);
        element.sendKeys(new CharSequence[]{text});
    }

    public int getSizeOfList(By locator){
        try {
            List<MobileElement> list= driver.findElements(locator);
            return list.size();

        }catch (Exception e){
            return 0;
        }
    }

    public int getSizeOfListForVisible(By locator){
        try {
            List<MobileElement> list= driver.findElements(locator);
            int count=0;
            for (int i = 0; i <list.size() ; i++) {
                MobileElement element=list.get(i);
                if (element.isDisplayed()){
                    ++count;
                }
            }
            return count;

        }catch (Exception e){
            return 0;
        }
    }

    public void clickElementInList(By locator,int index){

        try {

            List<MobileElement> list= driver.findElements(locator);
            clickElement(list.get(index));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public boolean clickElementInListViaAttributeAndData(By locator,String attributeName,String data){

        boolean elementExist=false;
        try {

            List<MobileElement> list= driver.findElements(locator);
            for (int i = 0; i <list.size() ; i++) {
                String getData=list.get(i).getAttribute(attributeName);
                if (getData.equals(data)){
                    elementExist=true;
                    list.get(i).click();
                    return true;
                }
            }

            if (!elementExist){
                Assert.fail(data+ " is not detected on the page!");
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return false;
    }


    public boolean waitElementWithThreadSleep(By locator, int second){

        boolean elementExist=false;
        try {
            elementExist= checkElementIsExist(locator);

            if (!elementExist){

                for (int i = 0; i < second; i++) {
                    Thread.sleep(1000);
                    elementExist=checkElementIsExist(locator);
                    if (elementExist){
                        elementExist=true;
                        break;
                    }
                }
            }

            Thread.sleep(1000);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return elementExist;
    }

    public boolean waitElementWithThreadSleepViaItem(By locator, int second){

        int item=0;
        boolean elementExist=false;
        try {
            item=getSizeOfList(locator);

            if (item==0){

                for (int i = 0; i < second; i++) {
                    Thread.sleep(1000);
                    item=getSizeOfList(locator);
                    if (item!=0){
                        elementExist=true;
                        break;
                    }
                }
            }else{
                elementExist=true;
            }
            Thread.sleep(1000);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return elementExist;
    }

    public String getData(By locator){

        try {
            MobileElement element= (MobileElement) driver.findElement(locator);
            return element.getText();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return "";
    }

    public String getData(By locator, String path){

        try {
            MobileElement element= (MobileElement) driver.findElement(locator);
            return element.findElement(By.xpath(path)).getText();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return "";
    }

    @And("^\"([^\"]*)\" icindeki veriyi \"([^\"]*)\" olarak kaydet$")
    public void setDataInHashMap(String jsonParameterName, String dataParameter){
        By locator=JsonMethods.getLocator(jsonParameterName);
        String value=getData(locator);
        KeepData.setKeepHashData(dataParameter,value);
        report.Report_Info(jsonParameterName+" icerisindeki veriyi "+value+" olarak kaydedilir");
    }

    @And("^\"([^\"]*)\" icindeki veriyi \"([^\"]*)\" karakteri ile bol ve \"([^\"]*)\" olarak kaydet$")
    public void setDataInHashMap(String jsonParameterName, String character, String dataParameter){
        By locator=JsonMethods.getLocator(jsonParameterName);
        String value=getData(locator);
        String[] array=value.split(Pattern.quote(character));
        String newValue=array[1].trim();
        KeepData.setKeepHashData(dataParameter,newValue);
        report.Report_Info(jsonParameterName+" icerisindeki veriyi '"+character+"' karakteri ile bol ve "
        +newValue+" olarak kaydet");
    }


    public void mouseHover(By locator){

        try {
            Actions builder = new Actions(driver);
            MobileElement element = (MobileElement) driver.findElement(locator);
            builder.moveToElement(element).build().perform();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public void mouseHover(MobileElement element){

        try {

            Actions builder = new Actions(driver);
            builder.moveToElement(element).build().perform();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }


    public void clickMobileElement(By locator) {
        MobileElement element = this.waitUntilVisibleByLocator(locator);
        this.clickMobileElement(element);
    }

    protected void clickMobileElement(MobileElement element) {
        this.waitUntilClickableByListOfElement(element).click();
    }


    public List<MobileElement> getMobileElementsViaAttribute(By locator){

        List<MobileElement> elements=null;

        try {

            elements= driver.findElements(locator);

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return elements;
    }

    public ArrayList getAllDataInList(By locator){

        ArrayList<String> list=new ArrayList();
        try {

            List<MobileElement> elementList= driver.findElements(locator);

            for (int i = 0; i <elementList.size() ; i++) {

                String data=elementList.get(i).getText();

                list.add(data);
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return list;

    }

    public String getAllDataInListViaString(By locator){

        String list="";
        try {

            List<MobileElement> elementList= driver.findElements(locator);

            for (int i = 0; i <elementList.size() ; i++) {

                String data=elementList.get(i).getText();
               if (i==(elementList.size()-1)){
                   list+=data;
               }else{
                   list+=data+", ";
               }
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return list;

    }

    public void clickElementInListViaContainsMethod(ArrayList<MobileElement> list,String data){

        try {

            for (int i = 0; i <list.size() ; i++) {

                if (list.get(i).toString().toLowerCase().contains(data.toLowerCase())){
                    clickMobileElement(list.get(i));
                    break;
                }

                if (i==list.size()-1){
                    Assert.fail("Aranan veri bulunamadi! Aranan Veri:"+data);
                }
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @And("^Geri butonuna tiklanir$")
    public void navigateBack(int times){
        try {
            for (int i = 0; i <times ; i++) {
                driver.navigate().back();
                driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
                checkPageIsReady();
                Thread.sleep(1000);
            }
            getLatestWindow();
            report.Report_Info("Geri butonuna tiklanir");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public synchronized void getLatestWindow() {
        windowList = new ArrayList<String>();
        Set<String> handles = getDriver().getWindowHandles();

        System.out.println("Parent Handle: " + handles);

        for (String s : handles) {
            System.out.println(s);
            windowList.add(windowList.size(), s);
        }
        getDriver().switchTo().window(windowList.get(windowList.size() - 1));
    }

    public int getWindowsSize() {
        Set<String> handles = getDriver().getWindowHandles();
        return handles.size();
    }

    public String getCurrentUrl(){

        try {
            return driver.getCurrentUrl();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return null;
    }


    public void changeURL(String url){

        try {

            driver.get(url);
            driver.manage().timeouts().pageLoadTimeout(120,TimeUnit.SECONDS);
            checkPageIsReady();
            getLatestWindow();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public String getDataViaJS(MobileElement element){

        try {

            JavascriptExecutor js = (JavascriptExecutor) driver;

            return js.executeScript("return arguments[0].value", element).toString();

        }catch (Exception e){
           Assert.fail(e.getMessage());
        }

        return null;
    }

    public String randomAlphaNumeric(int count) {

        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return (builder.toString()).toLowerCase();
    }

    public String createNumaricalNumber(int count) {

        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public int createRandomNumber(int maxNumber){
        try {
            Random random= new Random();
            return random.nextInt(maxNumber)+1;
        }catch (Exception e){
            return 0;
        }
    }

    public void switchiFrame(By locator){
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void moveToElement(By locator){
        try {
            Actions actions= new Actions(driver);
            actions.moveToElement(driver.findElement(locator)).build().perform();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public void loadAFile(By moveElementLocatar,By clickElementLocator,String filePath){

        try {
            // move to mouse on element
            moveToElement(moveElementLocatar);
            // send file path into element
            driver.findElement(clickElementLocator).sendKeys(filePath);
            Thread.sleep(2000);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public static int getNumbersInString(String data){
        try {
            return Integer.parseInt(data.replaceAll("\\D+",""));
        }catch (Exception e){
            return 0;
        }
    }

    public String getNumbersInStringReturnString(String data){
        try {
            return data.replaceAll("\\D+","");
        }catch (Exception e){
            return "";
        }

    }

    public String getDate(String dateFormat){
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date();
        return sdf.format(date);
    }

    public String getDateForPreviousMonth(String dateFormat,int howMuchMonthAgo){
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = Date.from(ZonedDateTime.now().minusMonths(howMuchMonthAgo).toInstant());
        return sdf.format(date);
    }

    public MobileElement getMobileElementInList(By locator,int index){
        MobileElement element=null;
        try {
            List<MobileElement> list=driver.findElements(locator);
            return list.get(index);

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return element;
    }

    public MobileElement getMobileElementInList(MobileElement element,By locator){
        MobileElement element1=null;
        try {
            element1=element.findElement(locator);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return element1;
    }

    public MobileElement getMobileElementInList(By locator,String attribute,String data){
        MobileElement element=null;
        try {
            List<MobileElement> list=driver.findElements(locator);
            for (int i = 0; i <list.size() ; i++) {
                String getData=list.get(i).getAttribute(attribute);
                if (getData.equals(data)){
                    element=list.get(i);
                }
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return element;
    }

    public void cleanInsideOfTextbox(By locator){
        try {
//            MobileElement element=driver.findElement(locator);
//            JavascriptExecutor js = (JavascriptExecutor)driver;
//            js.executeScript("arguments[0].value = '';", element);
            sendKeysToElement(locator," ");
            sendKeysToElement(locator,Keys.BACK_SPACE);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public static boolean linkExists(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeCurrentWindow(){
        ((JavascriptExecutor) driver)
                .executeScript("window.close();");
    }

    public boolean checkElementEnable(By locator){
        return driver.findElement(locator).isEnabled();
    }

    public boolean checkElementEnableInList(By locator,int index){
        List<MobileElement> list=driver.findElements(locator);
        return list.get(index).isEnabled();
    }

    public void refreshURL(){

        try {

            driver.navigate().refresh();
            driver.manage().timeouts().pageLoadTimeout(120,TimeUnit.SECONDS);
            checkPageIsReady();
            getLatestWindow();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    public static boolean deleteFile(String path,String fileName){
        try {
            File tempFile = new File(path+fileName);
            boolean exists = tempFile.exists();
            if (exists){
                tempFile.delete();
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return true;
    }

    public static void deleteDirectoryRecursion(String folderPath) {
      try {
          File file=new File(folderPath);
          if (file.isDirectory()) {
              File[] entries = file.listFiles();
              if (entries != null) {
                  for (File entry : entries) {
                      String path=entry.getPath();
                      deleteDirectoryRecursion(path);
                  }
              }
          }
          if (!file.delete()) {
              throw new IOException("Failed to delete " + file);
          }

      }catch (Exception e){
          e.getStackTrace();
      }
    }

    public static boolean checkFileExist(String path,String fileName){
        try {
            File tempFile = new File(path+fileName);
            return tempFile.exists();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return false;
    }

    public void openNewWindow(){
        try {
            ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(1000);
        }catch (Exception e){
            e.getStackTrace();
        }
    }


    public boolean elementIsSelected(By locator){
        try {
            return driver.findElement(locator).isSelected();
        }catch (Exception e){
         Assert.fail(e.getMessage());
        }
        return false;
    }

    public String getDataInList(By locator,int index){

        List<MobileElement> elements=null;
        String data=null;
        try {

            elements= driver.findElements(locator);
            data=elements.get(index).getText();

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return data;
    }

    public String convertArrayToString(String[] array){
        String data="";
        for (int i = 0; i <array.length ; i++) {
            if (i==(array.length-1)){
                data+=array[i];
            }else {
                data+=array[i]+", ";
            }
        }
        return data;
    }

    public String convertArrayListToString(ArrayList list){
        String data="";
        for (int i = 0; i <list.size() ; i++) {
            if (i==(list.size()-1)){
                data+=list.get(i);
            }else {
                data+=list.get(i)+", ";
            }
        }
        return data;
    }

    public boolean checkElementEnableViaAttribute(By locator,String attribute){
        String getData=getMobileElementInList(locator,0).getAttribute(attribute);
        return !getData.contains("disabled");
    }

    public static File[] findAllFiles(String path,String fileName){
        File[] files =null;
        try {
            File dir = new File(path+".");
            FileFilter fileFilter = new WildcardFileFilter(fileName);
            files = dir.listFiles(fileFilter);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return files;
    }

    public static boolean deleteAllFiles(String path,String fileName){
        try {
            File[] fileNames=findAllFiles(path,fileName);

            for (int i = 0; i <fileNames.length ; i++) {
               fileNames[i].delete();
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }        
        return false;
    }

    public static String  getFileName(String path,String regularExpression){
        String fileName="";
        try {
            File[] fileNames=findAllFiles(path,regularExpression);
            if (fileName.length()==0){
                for (int i = 0; i <6 ; i++) {
                    Thread.sleep(1000);
                    fileNames=findAllFiles(path,regularExpression);
                    if (fileName.length()!=0){
                        break;
                    }
                }
            }
            fileName= fileNames[0].getName();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return fileName;
    }

    public boolean clickElementInListViaData(By locator,String data){

        boolean elementExist=false;
        try {

            List<MobileElement> list= driver.findElements(locator);
            for (int i = 0; i <list.size() ; i++) {
                String getData=list.get(i).getText();
                if (getData.equals(data)){
                    elementExist=true;
                    list.get(i).click();
                    return true;
                }
            }

            if (!elementExist){
                Assert.fail(data+ " is not detected on the page!");
            }

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return false;
    }

    public static String getDataInStringViaRegex(String data,String regex){
       String getData="";
        Matcher matcher = Pattern.compile(regex).matcher(data);
        while(matcher.find()) {
            getData=matcher.group();
        }
       return getData;
    }

    @And("^Uygulamanin cache ini temizle$")
    public void cleanAppCache(){
        driver.resetApp();
        report.Report_Info("uygulamanin cache ini temizle");
    }

    public void hideKeybordForIOSDevice(){
        if (TestBase.PLATFORMNAME.equals("iOS")){
            try {
                By locator=By.name("Tamam");
                clickElementInList(locator,0);
                Thread.sleep(1000);
            }catch (Exception e){
                Assert.fail(e.getMessage());
            }
        }
    }




















}
