package com.dk.app.service.api;

import com.dk.app.dto.OTPRequest;
import com.dk.app.dto.SignUpForm;
import com.dk.app.repository.UserRepository;
import com.dk.app.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CustomerCamundaService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int serverPort;
    private static String BBUID = "BBUID";
    private static int count = 0;

    public ResponseEntity<?> validateOTPRestCall(OTPRequest otpRequest) {
        String payload = "{\n" +
                "    \"messageName\": \"StartOTPValidationMessage\",\n" +
                "    \"processInstanceId\": \""+otpRequest.getProcessInstanceId()+"\",\n" +
                "    \"processVariables\": {\n" +
                "        \"otprequest\":{\n" +
                "            \"value\": {\n" +
                "                \"email\": \""+otpRequest.getEmail()+"\",\n" +
                "                \"otp\": \""+otpRequest.getOtp()+"\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        String url = "http://CUSTOMER-CAMUNDA/engine-rest/message";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, request, Object.class);
        return responseEntity;
    }

    @CircuitBreaker(name = "registerCustomer", fallbackMethod = "fallbackRegisterCustomer")
    public ResponseEntity<?> registerCustomer(SignUpForm signUpForm) throws JSONException, IOException {
        count++;
        if(userRepository.existsByEmail(signUpForm.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Email is already in use!"));
        }


//        String payload = "{\n" +
//                "    \"variables\" : {\n" +
//                "        \"payload\" : {\n" +
//                "            \"value\" : {\n" +
//                "                \"name\": \""+signUpForm.getName()+"\",\n" +
//                "                \"email\": \""+signUpForm.getEmail()+"\",\n" +
//                "                \"role\":[\"admin\"],\n" +
//                "                \"password\": \""+signUpForm.getPassword()+"\"\n" +
//                "            }\n" +
//                "        } \n" +
//                "    },\n" +
//                "    \"businessKey\" : \""+(BBUID + serverPort + count)+"\",\n" +
//                "    \"withVariablesInReturn\" : true\n" +
//                "\n" +
//                "}";

        String payload = "\n" +
                "{\n" +
                "    \"variables\" : {\n" +
                "        \"payload\" : {\n" +
                "            \"value\" : {\n" +
                "                \"name\": \"Anil9\",\n" +
                "                \"email\": \"anil9@gmail.com\",\n" +
                "                \"role\":[\"admin\"],\n" +
                "                \"password\": \"123abc\"\n" +
                "            }\n" +
                "        } \n" +
                "    },\n" +
                "    \"businessKey\" : \"BBUID_serverPort_time_6\",\n" +
                "    \"withVariablesInReturn\" : true\n" +
                "\n" +
                "}";

        String url = "http://CUSTOMER-CAMUNDA/engine-rest/process-definition/key/customer_registration_id/start";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, request, Object.class);
        return responseEntity;
    }

    public ResponseEntity<?> fallbackRegisterCustomer (Throwable throwable){
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Customer Camunda API is falling!"));
    }
    
    
    
    
}
