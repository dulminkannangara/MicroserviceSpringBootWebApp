package com.dk.app.controller.servicetask.register.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CallSendOTP implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallSendOTP.class);

    @Override
    public void execute(DelegateExecution execution){
        LOGGER.info("Sending Patch Response to 'StartSendOTP' : processing");

        execution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("StartSendOTPMessage")
                .processInstanceId(execution.getProcessInstanceId())
                .correlate();

        LOGGER.info("Sent Patch Response to 'StartSendOTP' : success");
    }
}
