package com.dk.app.service.api;

import com.dk.app.dto.SignUpForm;
import com.dk.app.repository.UserRepository;
import com.dk.app.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @CircuitBreaker(name = "registerCustomer", fallbackMethod = "fallbackRegisterCustomer")
    public ResponseEntity<?> registerCustomer(SignUpForm signUpForm) throws JSONException {
        count++;
        if(userRepository.existsByEmail(signUpForm.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Email is already in use!"));
        }

        String payload = "{\n" +
                "    \"variables\" : {\n" +
                "        \"payload\" : {\n" +
                "            \"value\" : {\n" +
                "                \"name\": \"Anil8\",\n" +
                "                \"email\": \"anil8@gmail.com\",\n" +
                "                \"role\":[\"admin\"],\n" +
                "                \"password\": \"123abc\"\n" +
                "            }\n" +
                "        } \n" +
                "    },\n" +
                "    \"businessKey\" : \""+(BBUID + serverPort + count)+"\",\n" +
                "    \"withVariablesInReturn\" : true\n" +
                "\n" +
                "}";
        JSONObject jsonObj = new JSONObject(payload);

        String url = "http://CUSTOMER-CAMUNDA/engine-rest/process-definition/key/customer_registration_id/start";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, jsonObj, Object.class);
        return responseEntity;
    }

    public ResponseEntity<?> fallbackRegisterCustomer (Throwable throwable){
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Customer API is falling!"));
    }
}
