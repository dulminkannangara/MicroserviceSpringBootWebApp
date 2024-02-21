package com.dk.app.controller.servicetask.register.delegate.otpvalidation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidOTPNotify implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidOTPNotify.class);
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("Customer Registration OTP is matched");
        LOGGER.info("Sending Patch Response to 'OTTPVerificationPatchResponceMessage' : processing");

        Map<String,Object> responcePayload = new HashMap<>();
        responcePayload.put("isValidOTP","yes");

        execution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("OTTPVerificationPatchResponceMessage")
                .processInstanceId(execution.getProcessInstanceId())
                .setVariables(responcePayload)
                .correlate();
        LOGGER.info("Sent Patch Response to 'OTTPVerificationPatchResponceMessage' : success");
    }
}
