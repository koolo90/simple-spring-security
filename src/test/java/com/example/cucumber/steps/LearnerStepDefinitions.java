package com.example.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.*;

public class LearnerStepDefinitions {
    private String username;
    private String greeting;

    @Given("Username {string}")
    public void username(String username) {
        this.username = username;
    }

    @When("Say hello")
    public void sayHello() {
        this.greeting = "Greetings from " + username + "!";
    }

    @Then("Greeting was created")
    public void greetingWasCreated() {
        assertThat(greeting).isEqualTo("Greetings from Alice!");
    }

    @And("Wave")
    public void wave() {
        String[] hellos = this.greeting.split("from");
        this.greeting = hellos[0] + "and hugs from" + hellos[1];
    }

    @Then("Greeting was {string}")
    public void greetingWas(String expectedGreeting) {
        assertThat(greeting).isEqualTo(expectedGreeting);
    }
}
