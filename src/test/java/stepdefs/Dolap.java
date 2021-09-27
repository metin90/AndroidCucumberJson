package stepdefs;

import Pages.LoginPage;
import Pages.MainPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Dolap {

    MainPage mainPage=new MainPage();
    LoginPage loginPage=new LoginPage();

    @Given("^Uygulamanin cache ini temizle$")
    public void uygulamaCacheTemizle() {
        mainPage.cleanAppCache();
    }

    @Then("^Login butonuna tikla$")
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
