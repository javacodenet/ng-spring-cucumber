package com.acceptance.tests.selenium;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/ui",
        glue = {"com.acceptance.tests.selenium", "cucumber.api.spring"})
public class RunUITests {
}