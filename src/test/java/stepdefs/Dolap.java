package stepdefs;

import Pages.LoginPage;
import Pages.MainPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;


public class Dolap {

    MainPage mainPage=new MainPage();
    LoginPage loginPage=new LoginPage();

    @Given("^Uygulamanin cache ini temizle$")
    public void uygulamaCacheTemizle() {
        mainPage.cleanAppCache();
    }

    @And("^Login butonuna tikla$")
    public void loginButonunaTikla() {
        mainPage.clickLoginButton();
    }

    @And("^\"([^\"]*)\" kullanici adi girilir$")
    public void kullaniciAdiGirilir(String userName) {
        loginPage.typeUserNameLogin(userName);
    }

    @And("^\"([^\"]*)\" kullanici sifre girilir$")
    public void kullaniciSifreGirilir(String password) {
        loginPage.typePasswordLogin(password);
    }

    @And("^Giris yapmak icin Login butonuna tiklanir$")
    public void girisIcinLoginButonunaTikla() {
        loginPage.clickLoginButton();
    }





}
