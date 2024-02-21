package com.dk.app.controller.servicetask.register.delegate.otpvalidation;

import com.dk.app.dto.OTPRequest;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationOTP implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationOTP.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("ValidationOTP : processing");

        String customerOTP = execution.getVariable("customerOTP").toString().trim();
        String otp = execution.getVariable("otp").toString().trim();
        if (otp.equals(customerOTP)) {
            execution.setVariable("OTPValidation", "valid");
        }else{
            execution.setVariable("OTPValidation", "invalid");
        }

        LOGGER.info("ValidationOTP : End");

    }
}
