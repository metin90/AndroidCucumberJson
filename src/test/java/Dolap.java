import BaseFiles.TestBase;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.annotations.Test;

public class Dolap extends TestBase {

    @Test(description = "Login")
    public void GirisYap(){
        MainPage mainPage=new MainPage();
        LoginPage loginPage=new LoginPage();
        mainPage.cleanAppCache()
                .clickLoginButton();
        loginPage.typeUserNameLogin("sutravetre@biyac.com")
                .typePasswordLogin("Test*1234")
                .clickLoginButton();
    }

}
