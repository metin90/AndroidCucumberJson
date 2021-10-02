package Pages;

import GeneralFiles.BasePage;
import GeneralFiles.ReportMethods;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;


public class MainPage extends BasePage {

    ReportMethods reporter=new ReportMethods();

    By register_Btn=By.id("com.dolap.android:id/button_register");
    By login_Btn=By.id("com.dolap.android:id/button_login");

    public MainPage clickRegisterButton(){
        try {
            clickElement(register_Btn);
            Reporter.log("ÜYE OL butonuna tiklanmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return this;
    }


    public MainPage clickLoginButton(){
        try {
            clickElement(login_Btn);
            reporter.Report_Info("GİRİŞ YAP butonuna tiklanmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

        return this;
    }

    public MainPage cleanAppCache(){
        driver.resetApp();
        return this;
    }




}
