package runners;

import BaseFiles.TestBase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import org.testng.annotations.Test;


@CucumberOptions(
        features = "src/test/resources/features/LoginBasarisiz.feature",
        glue = {"stepdefs"},
        tags = {"@LoginBasarisizTest"},
        plugin = {

                "pretty",
                "html:target/cucumber-html-report",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "rerun:target/rerun.txt"
        }
//        ,format = {
//                "pretty",
//                "html:target/cucumber-reports/cucumber-pretty",
//                "json:target/cucumber-reports/CucumberTestReport.json",
//                "rerun:target/cucumber-reports/rerun.txt"
//        }
        )

public class Login_Basarisiz_Runner extends TestBase {


    @Test(description="loginBasarisiz",dataProvider="features")
    public void feature2(PickleEventWrapper eventWrapper, CucumberFeatureWrapper cucumberFeature) throws Throwable {
        //testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
        testNGCucumberRunner.runScenario(eventWrapper.getPickleEvent());
    }

}
