package com.example.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class UiStepDefs {
    private WebDriver driver;

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.get(url);
    }

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @When("I go to {string}")
    public void iGoTo(String page) {
        driver.get(page);
    }

    @Then("the title should contain {string}")
    public void theTitleShouldContain(String title) {
        assertThat(driver.getTitle()).isEqualTo(title);
    }


}
