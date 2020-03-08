package com.saviors.stepdefs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.stream.IntStream;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;
import com.saviors.config.Configuration;
import com.saviors.domain.Deed;
import com.saviors.service.DeedsService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeedServiceTest {

  private static final int LIST_LIMIT = 10;

  @InjectMocks
  private DeedsService deedService;

  @Mock
  private Configuration configuration;

  private List<Deed> outputList;

  @Before
  public void init() throws NoSuchFieldException, SecurityException {
    MockitoAnnotations.initMocks(this);
    FieldSetter.setField(deedService, deedService.getClass().getDeclaredField("listLimit"),
        LIST_LIMIT);
  }

  @Given("Users have added more than the maximum good deeds")
  public void the_users_have_added_more_than_the_maximum_good_deeds() {
    repeat(LIST_LIMIT + 1, () -> deedService.addDeed(new Deed("foo")));
  }

  @When("Good deeds are requested")
  public void good_deeds_are_requested() {
    outputList = deedService.getDeeds();
  }

  @Then("The maximum number of good deeds is returned")
  public void the_maximum_number_of_good_deeds_is_returned() {
    assertTrue(outputList.size() == LIST_LIMIT);
  }

  @Given("Some good deeds have been added")
  public void some_good_deeds_have_been_added() {
    deedService.addDeed(new Deed("old deed"));
    deedService.addDeed(new Deed("new deed"));
  }

  @When("Good deeds is requested")
  public void good_deeds_is_requested() {
    outputList = deedService.getDeeds();
  }

  @Then("Good deeds are returned in reverse order")
  public void good_deeds_are_returned_in_reverse_order() {
    assertTrue(outputList.size() == 2);
    assertEquals("new deed", outputList.get(0).getText());
    assertEquals("old deed", outputList.get(1).getText());
  }

  private static void repeat(int count, Runnable action) {
    IntStream.range(0, count).forEach(i -> action.run());
  }

}