package com.acceptance.tests.cucumber;


import com.demo.Application;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AppInitialiser {

    @Before
    public void setup() {

    }
}
