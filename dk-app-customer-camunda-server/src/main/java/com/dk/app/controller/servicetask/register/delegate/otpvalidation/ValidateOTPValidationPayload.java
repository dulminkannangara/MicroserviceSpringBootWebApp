package com.dk.app.controller.servicetask.register.delegate.otpvalidation;

import com.dk.app.dto.OTPRequest;
import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ValidateOTPValidationPayload implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateOTPValidationPayload.class);

    @Autowired
    private UserService service;

    @Override
    public void execute(DelegateExecution execution){
        try {
            LOGGER.info("ValidatePayload : processing");
            LinkedHashMap<String, String> payload_values = (LinkedHashMap<String, String>) execution.getVariable("otprequest");
            OTPRequest payload = service.getOTPPayload(payload_values);
            execution.setVariable("customerOTP",payload.getOtp());
            execution.setVariable("hasCustomerOTP",(payload.getOtp() == null || payload.getOtp().isEmpty()) ? "no":"yes");
            LOGGER.info("ValidatePayload : success");
        }catch (Exception exception){
            LOGGER.error(exception.getMessage(),exception);
            execution.createIncident("payload error", "ValidatePayload", exception.getMessage());
            throw exception;
        }
    }
}
