package playwright;

import com.microsoft.playwright.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlaywrightClass {
    public static void main(String[] args) {
        //get viewport size of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //declare User-Agent headers
        Map<String, String> headers = new HashMap<>();

        /*
        //initialize Playwright
        try(Playwright playwright = Playwright.create()){
            //launch browser
            Browser browser = playwright.chromium()
                    .launch((new BrowserType.LaunchOptions()
                                    .setHeadless(false)));
            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
            Page page = browserContext.newPage();

            // Set a common User-Agent header
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
            page.setExtraHTTPHeaders(headers);

            //open page
            //https://www.webdriveruniversity.com/
            //https://www.google.com/
            page.navigate("https://www.webdriveruniversity.com/");
            Thread.sleep(100);

            System.out.println(page.title());
            page.close();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

    }
}
