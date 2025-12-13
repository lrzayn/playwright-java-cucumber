package browser;

import com.microsoft.playwright.*;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserManager {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BrowserManager.class);
    public Playwright playwright; //use to create an instance of Chrome, Firefox etc.
    public Page page; //is the single tab in the browser
    public BrowserContext context; // is the isolated browser session
    public Browser browser; //represent the browser instance
    public Properties properties;
    private static final Logger logger = Logger.getLogger(BrowserManager.class.getName());

    public  BrowserManager(){
        properties = new Properties();
        //creates a path to a configuration file. If "config.path" isn't set,
        //it defaults to a file located in "src/main/resources/config.properties
        Path configPath = Paths.get(System.getProperty("config.path",
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
                        "config.properties").toString()));
        try(InputStream input = Files.newInputStream(configPath)){
            properties.load(input);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load properties file.", e);
        }
    }

    public byte[] takeScreenshots(){
        if(page != null){
            return page.screenshot();
        }
        return new byte[0];
    }

    public Map<String, String> headers = new HashMap<>();

    public void setUp(){
        System.out.println("Setting up Playwright...");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //initialize Playwright and launch browser
        playwright = Playwright.create();

        String browserType = properties.getProperty("browser", "chromium");

        switch (browserType.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
                headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                logger.warning("Unsupported browser type: " + browserType + ". Defaulting to chromium.");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
                headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

                break;
        }
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        logger.info("Playwright setup complete!");
        /*
        browser = playwright.chromium().launch((new BrowserType.LaunchOptions().setHeadless(false)));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

        page = context.newPage();
        page.setExtraHTTPHeaders(headers);
        System.out.println("Playwright setup complete.");
        */
    }

    public void tearDown(){
        //System.out.println("Tearing down Plaiwright...");
        logger.info("Tearing down Plaiwright...");
        if(page != null)page.close();
        if(browser != null)browser.close();
        if(playwright != null)playwright.close();
        //System.out.println("Playwright teardown complete.");
        logger.info("Playwright teardown complete.");
    }
}
