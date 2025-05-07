package com.raj.bankingapp;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {"com.raj.bankingapp.steps", "com.raj.bankingapp"},
    plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberRunnerTest {

}