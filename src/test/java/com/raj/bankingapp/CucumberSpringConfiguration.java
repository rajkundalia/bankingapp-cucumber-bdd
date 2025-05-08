package com.raj.bankingapp;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@CucumberContextConfiguration
@SpringBootTest(classes = BankingappApplication.class)
@Profile("test")
public class CucumberSpringConfiguration {
    // This class can be empty - it just serves as a holder for annotations
}