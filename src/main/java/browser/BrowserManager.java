package browser;

import com.microsoft.playwright.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BrowserManager {

    public Playwright playwright; //use to create an instance of Chrome, Firefox etc.
    public Page page; //is the single tab in the browser
    public BrowserContext context; // is the isolated browser session
    public Browser browser; //represent the browser instance

    public Map<String, String> headers = new HashMap<>();

    public void setUp(){
        System.out.println("Setting up Playwright...");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //initialize Playwright and launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch((new BrowserType.LaunchOptions().setHeadless(false)));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));

        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");

        page = context.newPage();
        page.setExtraHTTPHeaders(headers);
        System.out.println("Playwright setup complete.");
    }

    public void tearDown(){
        System.out.println("Tearing down Plaiwright...");
        if(page != null)page.close();
        if(browser != null)browser.close();
        if(playwright != null)playwright.close();
        System.out.println("Playwright teardown complete.");
    }
}
