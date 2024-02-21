package com.dk.app.controller.servicetask.register.delegate;

import com.dk.app.dto.ApiResponse;
import com.dk.app.dto.SignUpForm;
import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SaveCustomerDetails implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveCustomerDetails.class);
    @Autowired
    private UserService service;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("Register Customer : processing");
        SignUpForm payload = (SignUpForm) execution.getVariable("payload");
        ResponseEntity<ApiResponse> response = service.registerUser(payload);
        LOGGER.info("Register Customer : "+response.getBody().getMessage());
    }
}
