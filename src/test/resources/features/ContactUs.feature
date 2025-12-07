Feature: WebdriverUniversity.com - Contact Us Page

  Background: Pre Conditions
    Given I navigate to the webdriveruniversity homepage
    When I click on the contact us button

  Scenario: Valid Contact Us Form Submission
    And I type a first name
    And I type a last name
    And I enter email address
    And I type a comment
    And I click on the submit button
    Then I should be presented with a successful contact us submission message

  Scenario: Invalid Contact Us Form Submission
    And I type a first name
    And I type a last name
    #And I enter email address
    And I type a comment
    And I click on the submit button
    Then I should be presented with an unsuccessful contact us submission message
#
#    #Regular expression input
#  Scenario: Valid Contact Us Form Submission
#    And I type a specific first name "Edvard"
#    And I type a specific last name "Bonson"
#    And I enter a specific email address "edvard.bonson@sample.com"
#    And I type a specific comment "Hello EB" and number 2 within comment input field
#    And I click on the submit button
#    Then I should be presented with a successful contact us submission message
#
#    #Random input
#  Scenario: Valid Contact Us Form Submission - Using Random Data
#    And I type a random first name
#    And I type a random last name
#    And I enter a random email address
#    And I type a comment
#    And I click on the submit button
#    Then I should be presented with a successful contact us submission message

    #Scenario Outline
  Scenario Outline: Valid Contact Us Page
    And I type a first name "<firstName>"
    And I type a last name "<lastName>"
    And I enter a email address "<emailAddress>"
    And I type a comment "<comment>"
    And I click on the submit button
    Then I should be presented with a header text "<message>"

    Examples:
      | firstName | lastName | emailAddress           | comment         | message   |
      | John      | Doe      | john.doe@example.com   | Hello           | Thank You |
      | Mia       | Carter   | mia.carter@example.com | Hello  from Mia | Thank You |
      | Mia       | Carter   | mia.carter             | Hello  from Mia | Error     |

