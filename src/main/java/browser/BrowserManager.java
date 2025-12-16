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

    // A thread local variable for each thread that don't share the data to other threads.
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>(); //used to create an instance of the Chromium, Firefox browser etc.
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>(); //represents the browser instance.
    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>(); //is the isolated browser session.
    private static final ThreadLocal<Page> page = new ThreadLocal<>(); //is the single tab or window in the browser.

    public Properties properties;
    private static final Logger logger = Logger.getLogger(BrowserManager.class.getName());

    public Map<String, String> headers = new HashMap<>();

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

    public Page getPage() {
        return page.get();
    }
    public void setPage(Page newPage) {
        page.set(newPage);
    }

    public BrowserContext getBrowserContext() {
        return browserContext.get();
    }
    public void setBrowserContext(BrowserContext newBrowserContext) {
        browserContext.set(newBrowserContext);
    }

    public byte[] takeScreenshots(){
        if(page.get() != null){
            return page.get().screenshot();
        }
        return new byte[0];
    }

    public void setUp(){
        logger.info("Setting up Playwright...");
        //System.out.println("Setting up Playwright...");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        try {
            playwright.set(Playwright.create());

            String browserType = properties.getProperty("browser", "chromium");

            switch (browserType.toLowerCase()) {
                case "chromium":
                    browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    browserContext.set(browserContext.get().browser().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
                    //context.set(context.get().browser().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
                    headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
                    break;
                case "firefox":
                    browser.set(playwright.get().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    break;
                default:
                    logger.warning("Unsupported browser type: " + browserType + ". Defaulting to chromium.");
                    browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                    //browser = playwright      .chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

                    browserContext.set(browserContext.get().browser().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
                    //context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
                    //context = browser                 .newContext(new Browser.NewContextOptions().setViewportSize(width, height));
                    headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

                    break;
            }
            browserContext.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
            page.set(browserContext.get().newPage());
            logger.info("Playwright setup complete!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to setup Playwright! ", e);
        }

        /*
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
        */
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

        try{
            //System.out.println("Tearing down Plaiwright...");
            logger.info("Tearing down Plaiwright...");
            if(page.get() != null) page.get().close();
            if(browserContext.get() != null) browserContext.get().close();
            if(browser.get() != null) browser.get().close();
            if(playwright.get() != null)playwright.get().close();
            //System.out.println("Playwright teardown complete.");
            logger.info("Playwright teardown complete.");
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Failed to close Playwright resources! ", e);
        }
    }
}
