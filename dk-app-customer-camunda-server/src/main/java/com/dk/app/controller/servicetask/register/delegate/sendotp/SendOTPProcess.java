package com.dk.app.controller.servicetask.register.delegate.sendotp;

import com.dk.app.dto.SignUpForm;
import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendOTPProcess implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendOTPProcess.class);
    @Autowired
    private UserService service;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String otp;
        try {
            SignUpForm payload = (SignUpForm) execution.getVariable("payload");
            LOGGER.info("SendOTP : processing");
            otp = service.sendOTP(payload);
            execution.setVariable("otp", otp);
            LOGGER.info("SendOTP : Done");
            execution.setVariable("hasCustomerOTP", "no");

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            execution.createIncident("SendOTPProcess Error", "SendOTPProcess", exception.getMessage());
            throw exception;
        }
    }
}
