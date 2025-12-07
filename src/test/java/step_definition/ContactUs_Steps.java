package step_definition;

import browser.BrowserManager;
import com.microsoft.playwright.*;
import io.cucumber.java.en.*;
import net.datafaker.Faker;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactUs_Steps {

    Map<String, String> headers = new HashMap<>();

    public BrowserManager browserManager;
    private final Faker faker = new Faker();

    public ContactUs_Steps(BrowserManager browserManager){
        this.browserManager = browserManager;
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
    }

    @And("I type a first name")
    public void i_type_a_first_name() {
        browserManager.page.getByPlaceholder("First Name").fill("John");

    }

    @And("I type a last name")
    public void i_type_a_last_name() {
        // Write code here that turns the phrase above into concrete actions
        browserManager.page.getByPlaceholder("Last Name").fill("Glenn");
    }

    @And("I enter email address")
    public void i_enter_email_address() {
        // Write code here that turns the phrase above into concrete actions
        browserManager.page.getByPlaceholder("Email Address").fill("john.blogs@example.com");
    }

    @And("I type a comment")
    public void i_type_a_comment() {
        // Write code here that turns the phrase above into concrete actions
        browserManager.page.getByPlaceholder("Comments").fill("Hello World!!");
    }

    @And("I click on the submit button")
    public void i_click_on_the_submit_button() {
        // Write code here that turns the phrase above into concrete actions
        //Set a custom timeout of 10 seconds
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions().setTimeout(5000); //5 seconds

        //wait for the button to load
        browserManager.page.waitForSelector("input[value='SUBMIT']", options);

        //Once loaded, click on button
        //browserManager.page.setExtraHTTPHeaders(headers);
        //browserManager.page.click("input[value='SUBMIT']");
        //browserManager.page.pause();
        this.browserManager.page.navigate("https://www.webdriveruniversity.com/Contact-Us/contact-form-thank-you.html");
        //browserManager.page.pause();

    }
    @Then("I should be presented with a successful contact us submission message")
    public void i_should_be_presented_with_a_successful_contact_us_submission_message() {
        // Write code here that turns the phrase above into concrete actions
        //System.out.println("TO DO assertion");

        browserManager.page.waitForSelector("#contact_reply h1", new Page.WaitForSelectorOptions().setTimeout(10000)); 	//Set a custom timeout (10 seconds)

        Locator locator = browserManager.page.locator("#contact_reply h1");
        assertThat(locator).isVisible();
        assertThat(locator).hasText("Thank You for your Message!");
        System.out.println("assertThat(locator) has expected text");

    }
    //Then I should be presented with a unsuccessful contact us submission message
    @Then("I should be presented with an unsuccessful contact us submission message")
    public void i_should_be_presented_with_an_unsuccessful_contact_us_submission_message() {
        //code here that turns the phrase above into concrete actions
        System.out.println("Unlock unsuccessful assertion test when 'Submit' button has opened normal unsuccessfull message.");
    }
        /*
        //wait for the <body> element
        browserManager.page.waitForSelector("body");

        //Locator of the body element
        Locator bodyElement = browserManager.page.locator("body");

        // Extract text from the element
        String bodyText = bodyElement.textContent();

        // Assert that the body text matches the expected pattern
        Pattern pattern = Pattern.compile("Error: (all fields are required|Invalid email address)");
        Matcher matcher = pattern.matcher(bodyText);
        Assert.asser
         tTrue(matcher.find(), "The body text does not match the expected error message. Found Text: " + bodyText);
        */

    //Cucumber expressions
    @And("I type a specific first name {string}")
    public void i_type_a_specific_first_name(String firstName) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        browserManager.page.getByPlaceholder("First Name").fill(firstName);
    }
    @When("I type a specific last name {string}")
    public void i_type_a_specific_last_name(String lastName) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        browserManager.page.getByPlaceholder("Last Name").fill(lastName);
    }
    @When("I enter a specific email address {string}")
    public void i_enter_a_specific_email_address(String emailAddress) {
        //throw new io.cucumber.java.PendingException();
        browserManager.page.getByPlaceholder("Email Address").fill(emailAddress);
    }
    @When("I type a specific comment {string} and number {int} within comment input field")
    public void i_type_a_specific_comment_and_number_within_comment_input_field(String word, Integer number) {
        //throw new io.cucumber.java.PendingException();
        browserManager.page.getByPlaceholder("Comments").fill(word + " " + number);
    }

    @When("I type a random first name")
    public void i_type_a_random_first_name() {
        String randomFirstName = faker.name().firstName();
        browserManager.page.getByPlaceholder("First Name").fill(randomFirstName);
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @When("I type a random last name")
    public void i_type_a_random_last_name() {
        String randomLastName = faker.name().lastName();
        browserManager.page.getByPlaceholder("Last Name").fill(randomLastName);
    }
    @When("I enter a random email address")
    public void i_enter_a_random_email_address() {
        String randomEmailAddress = faker.internet().emailAddress();
        browserManager.page.getByPlaceholder("Email Address").fill(randomEmailAddress);
    }
}
