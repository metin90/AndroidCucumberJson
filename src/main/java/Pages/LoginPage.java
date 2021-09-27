package Pages;

import BaseFiles.AndroidConstants;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

public class LoginPage extends MainPage {

    By userNameLogin_TextBox=By.id("com.dolap.android:id/edittext_email_username");
    By passwordLogin_TextBox=By.id("com.dolap.android:id/edittext_password");
    By buttonLogin_Btn=By.id("com.dolap.android:id/button_login");
    By search_Txtbx=By.id(AndroidConstants.appPackage+":id/textViewSearchBar");


    public LoginPage typeUserNameLogin(String name){
        try {
            sendKeysToElement(userNameLogin_TextBox,name);
            reporter.Report_Info(name+ " giris yap sayfasında kullanıcı adı alanina girilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public LoginPage typePasswordLogin(String passwordLogin){
        try {
            sendKeysToElement(passwordLogin_TextBox,passwordLogin);
            reporter.Report_Info(passwordLogin+ " giris yap sayfasında sifre alanina girilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public LoginPage clickLoginButton(){
        try {
            clickElement(buttonLogin_Btn);
            reporter.Report_Info("Giris yap butonuna tiklanmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public LoginPage typeSearchCriteria(String text){
        try {
            sendKeysToElement(search_Txtbx,text);
            reporter.Report_Info(text+ "was typed in searchbox. ");

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

}
