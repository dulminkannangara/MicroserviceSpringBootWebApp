package com.dk.app.controller.servicetask.register.delegate;

import com.dk.app.dto.SignUpForm;
import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;

@Component
public class ValidatePayload implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatePayload.class);

    @Autowired
    private UserService service;

    @Override
    public void execute(DelegateExecution execution){
        try {
            LOGGER.info("ValidatePayload : processing");
            LinkedHashMap<String, Object> payload_values = (LinkedHashMap<String, Object>) execution.getVariable("payload");
            SignUpForm payload = service.getPayload(payload_values);
            execution.setVariable("payload",payload);
            LOGGER.info("ValidatePayload : success");
        }catch (Exception exception){
            LOGGER.error(exception.getMessage(),exception);
            execution.createIncident("payload error", "ValidatePayload", exception.getMessage());
            throw exception;
        }
    }
}
