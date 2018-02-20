package com.acceptance.tests.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/api",
        glue = {"com.acceptance.tests.cucumber", "cucumber.api.spring"})
public class RunAPITests {
}