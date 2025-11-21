package step_definition;

import browser.BrowserManager;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Homepage_Steps {

    public BrowserManager browserManager;
    //Map<String, String> headers = new HashMap<>();

    public Homepage_Steps(BrowserManager browserManager){
        this.browserManager = browserManager;
    }
    @Given("I navigate to the webdriveruniversity homepage")
    public void i_navigate_to_the_webdriveruniversity_homepage() {
        //https://www.webdriveruniversity.com/
        browserManager.page.navigate("https://www.webdriveruniversity.com/").headers();
    }

    @When("I click on the contact us button")
    public void i_click_on_the_contact_us_button() {
        //page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CONTACT US Contact Us Form")).click();
        /*
        this.browserManager.page = browserManager.context.waitForPage(() -> {
            browserManager.page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CONTACT US Contact Us Form")).click();
        });
        this.browserManager.page.bringToFront();
        */
        //browserManager.page.setExtraHTTPHeaders(headers);
        this.browserManager.page.navigate("https://www.webdriveruniversity.com/Contact-Us/contactus.html");
        //browserManager.page.pause();
    }
}
