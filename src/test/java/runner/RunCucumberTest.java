package runner;

import browser.BrowserManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@CucumberOptions(
        //features = "src/test/resources/features/Login.feature",
        features = "src/test/resources/features",
        glue = "step_definition",
        //tags = "@contact-us and not @ignore",
        tags = "@regression and not @ignore",
        //tags = "@login and not @ignore",
        //tags = "@smoke",
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-report.html"}
)

//test runner
public class RunCucumberTest extends AbstractTestNGCucumberTests {

    private static final Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(RunCucumberTest.class.getName());

    //Static block to load config.properties
    static {
        Path configPath = Paths.get(System.getProperty("config.path",
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
                        "config.properties").toString()));
        try(InputStream input = Files.newInputStream(configPath)){
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load properties file.", e);
        }
    }

    public static void main(String[] args){
        //THe instance og TestNG
        TestNG testNG = new TestNG();

        //TestNG suite instance
        XmlSuite suite = new XmlSuite();

        //Get thread amount from config.properties file
        int threadCount = getThreadCount();
        System.out.println("Configure thread count value: " + threadCount);

        //Set the number
        suite.setDataProviderThreadCount(threadCount);

        //TestNG test in the suite
        XmlTest test = new XmlTest(suite);
        test.setName("Cucumber test"); //Set the name of tests
        //Add the test class to the test
        test.setXmlClasses(Collections.singletonList(new XmlClass(RunCucumberTest.class)));

        //Disable default listeners (disable TestNG reports from being generated
        testNG.setUseDefaultListeners(false);

        //Add the suite tp the TestNG instance
        testNG.setXmlSuites(Collections.singletonList(suite));

        //Run TestNG with the configured suite
        testNG.run();
    }

    //Method to read file
    private static int getThreadCount(){
        return Integer.parseInt(properties.getProperty("thread.count", "1"));
    }

    //DataProvider method
    //use for parallel execution
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        //Provide data for test execution
        return super.scenarios();
    }
}
