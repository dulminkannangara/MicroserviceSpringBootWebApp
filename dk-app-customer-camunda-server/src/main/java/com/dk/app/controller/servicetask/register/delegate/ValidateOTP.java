package com.dk.app.controller.servicetask.register.delegate;

import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

public class ValidateOTP implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }
}
