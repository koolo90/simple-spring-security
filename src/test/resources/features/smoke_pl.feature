@ui
Feature: Login Page

  Scenario: Open login page
    Given I open the browser
    When I go to "http://127.0.0.1:8080/login"
    Then the title should contain "Login"