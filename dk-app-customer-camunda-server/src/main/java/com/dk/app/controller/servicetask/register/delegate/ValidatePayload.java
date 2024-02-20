package com.dk.app.controller.servicetask.register.delegate;

import com.dk.app.dto.SignUpForm;
import com.dk.app.service.register.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

public class ValidatePayload implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatePayload.class);

    @Autowired
    private UserService service;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            LOGGER.info("ValidatePayload : processing");
            LinkedHashMap<String, String> payload_values = (LinkedHashMap<String, String>) execution.getVariable("payload");
            SignUpForm payload = service.getPayload(payload_values);
            execution.setVariable("payload",payload);
            LOGGER.info("ValidatePayload : success");
        }catch (Exception exception){
            LOGGER.error(exception.getMessage(),exception);
            execution.createIncident("payload error", "ValidatePayload", exception.getMessage());
            throw exception;
//            Context.getCommandContext().getTransactionContext().addTransactionListener(TransactionState.COMMITTED, commandContext -> {
//                execution.getProcessEngine().getRuntimeService().suspendProcessInstanceById(execution.getProcessInstanceId());
//            });
//            execution.getProcessEngine().getRuntimeService().suspendProcessInstanceById(execution.getProcessInstanceId());

        }
    }
}
