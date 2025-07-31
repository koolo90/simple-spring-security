@smoke
Feature: Hello

  @fast
  Scenario: Saying hello
    Given Username "Alice"
    When Say hello
    Then Greeting was "Greetings from Alice!"

  @slow
  Scenario: Saying hello with waving
    Given Username "Alice"
    When Say hello
    And Wave
    Then Greeting was "Greetings and hugs from Alice!"