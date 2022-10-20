package ru.netology.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "summary"},
        features = {"src/test/resources/Transfer.feature"},
        glue = {"ru.netology.cucmberSteps"})
public class CardBalanceTest {
}