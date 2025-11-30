Feature: WebdriverUniversity.com - Contact Us Page

  Scenario: Valid Contact Us Form Submission
    Given I navigate to the webdriveruniversity homepage
    When I click on the contact us button
    And I type a first name
    And I type a last name
    And I enter email address
    And I type a comment
    And I click on the submit button
    Then I should be presented with a successful contact us submission message

  Scenario: Valid Contact Us Form Submission
    Given I navigate to the webdriveruniversity homepage
    When I click on the contact us button
    And I type a first name
    And I type a last name
    #And I enter email address
    And I type a comment
    And I click on the submit button
    Then I should be presented with an unsuccessful contact us submission message
#
#    #Invalid input negative scenario
#  Scenario: Valid Contact Us Form Submission
#    Given I navigate to the webdriveruniversity homepage
#    When I click on the contact us button
#    And I type a specific first name "Edvard"
#    And I type a specific last name "Bonson"
#    And I enter a specific email address "edvard.bonson@sample.com"
#    And I type a specific comment "Hello EB" and number 2 within comment input field
#    And I click on the submit button
#    Then I should be presented with a successful contact us submission message

