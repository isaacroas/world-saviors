package com.saviors.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "classpath:com/saviors/stepdefs", tags = {"not @stress", "not @integration"})
public class JUnitRunnerUnit {
}
