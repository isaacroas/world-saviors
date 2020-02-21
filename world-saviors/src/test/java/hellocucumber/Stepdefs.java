package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

class IsItFriday {
  static String isItFriday(String today) {
    return "Nope";
  }
}

public class Stepdefs {
  
  private String today;
  private String actualAnswer;

  @Given("today is Sunday")
  public void today_is_Sunday() {
    this.today = "Sunday";
  }

  @When("I ask whether it's Friday yet")
  public void i_ask_whether_it_s_Friday_yet() {
    this.actualAnswer = IsItFriday.isItFriday(today);
  }

  @Then("I should be told {string}")
  public void i_should_be_told(String expectedAnswer) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(expectedAnswer, actualAnswer);
  }
}
