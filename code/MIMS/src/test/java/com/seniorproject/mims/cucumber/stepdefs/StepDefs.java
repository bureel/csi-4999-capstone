package com.seniorproject.mims.cucumber.stepdefs;

import com.seniorproject.mims.MimsApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MimsApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
