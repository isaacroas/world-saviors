package com.saviors.junit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "classpath:com/saviors/stepdefs",
    tags = {"@stress"})
public class StressJUnitRunner {
}
