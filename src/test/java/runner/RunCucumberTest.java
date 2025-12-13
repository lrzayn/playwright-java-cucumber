package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        //features = "src/test/resources/features/Login.feature",
        features = "src/test/resources/features",
        glue = "step_definition",
        tags = "@contact-us and not @ignore",
        //tags = "@regression"
        //tags = "@login"
        //tags = "@smoke"
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-report.html"}
)

//test runner
public class RunCucumberTest extends AbstractTestNGCucumberTests {

}
