package com.dk.app.service.api;

import com.dk.app.dto.SignUpForm;
import com.dk.app.repository.UserRepository;
import com.dk.app.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "registerCustomer", fallbackMethod = "fallbackRegisterCustomer")
    public ResponseEntity<ApiResponse> registerCustomer(SignUpForm signUpForm){

        if(userRepository.existsByEmail(signUpForm.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Email is already in use!"));
        }
        String url = "http://CUSTOMER-SERVICE/api/customer/signup";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ApiResponse> responseEntity = restTemplate.postForEntity(url, signUpForm, ApiResponse.class);
        return responseEntity;
    }

    public ResponseEntity<ApiResponse> fallbackRegisterCustomer (Throwable throwable){
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Customer API is falling!"));
    }

}
