package com.dk.app.controller.servicetask.register.delegate.otpvalidation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class InvalidOTPNotify implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }
}
