package Pages;

import GeneralFiles.BasePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

public class RegistrationPage extends MainPage {

    By userName_TextBox=By.id("com.dolap.android:id/edittext_username");
    By email_TextBox=By.id("com.dolap.android:id/edittext_email");
    By password_TextBox=By.id("com.dolap.android:id/layoutPasswordInput_editText_password");
    By userAgreement_TextBox=By.id("com.dolap.android:id/imageview_user_agreement");
    By campaignAgreement_TextBox=By.id("com.dolap.android:id/imageview_campaign_agreement");
    By uyeOlButon_TextBox=By.id("com.dolap.android:id/button_register");


    public RegistrationPage typeUserName(){
        try {
            String name="tanApaydin";
            sendKeysToElement(userName_TextBox,name);
            reporter.Report_Info(name+ " user name alanina girilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public RegistrationPage typeEposta(){
        try {
            String ePosta="apaydin@yposta.net";
            sendKeysToElement(email_TextBox,ePosta);
            reporter.Report_Info(ePosta+ " ePosta alanina girilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public RegistrationPage typePassword(){
        try {
            String password="Apaydin1";
            sendKeysToElement(password_TextBox,password);
            reporter.Report_Info(password+ " sifre alanina girilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public RegistrationPage typeUserAgreement(){
        try {

            clickElement(userAgreement_TextBox);
            //Reporter.log(userAggrement+ " userAggrement alanı secilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public RegistrationPage typeCampaignAgreement(){
        try {

            clickElement(campaignAgreement_TextBox);
            //Reporter.log(campaignAggrement+ " campaignAggrement alanı secilmistir.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }

    public RegistrationPage typeUyeOl(){
        try {

            clickElement(uyeOlButon_TextBox);
            //Reporter.log(uyeOl+ " uyeOl butonuna tıklanmıstır.");
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        return this;
    }




}
