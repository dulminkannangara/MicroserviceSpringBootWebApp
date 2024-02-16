package com.dk.app.microservice.service;

import com.dk.app.dto.SignUpForm;
import com.dk.app.hystrix.CommonHysctrixCommand;
import com.dk.app.microservice.client.CustomerServiceAPI;
import com.dk.app.repository.UserRepository;
import com.dk.app.response.ApiResponse;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class CustomerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpClient client;

    @Autowired
    CustomerServiceAPI customerServiceAPI;

    @Autowired
    HystrixCommand.Setter setter;

    public ResponseEntity<ApiResponse> registerUser(SignUpForm signUpRequest) throws ExecutionException, InterruptedException {

        CommonHysctrixCommand<ResponseEntity<ApiResponse>> command = new CommonHysctrixCommand<ResponseEntity<ApiResponse>>(setter,
                ()->{
                    if(userRepository.existsByEmail(signUpRequest.getEmail())) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Email is already in use!"));
                    }
                    String payload = "{\n" +
                            "    \"name\": \""+signUpRequest.getName()+"\",\n" +
                            "    \"email\": \""+signUpRequest.getEmail()+"\",\n" +
                            "    \"role\":[\"admin\"],\n" +
                            "    \"password\": \""+signUpRequest.getPassword()+"\"\n" +
                            "}";
                    String customerUrl = customerServiceAPI.getCustomerAPIUrl();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(customerUrl+"/api/customer/auth/signup"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(payload))
                            .build();
                    HttpResponse response = null;
                    try {
                        response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Customer API Falling!"));
                    }
                    return (ResponseEntity<ApiResponse>) response.body();
                },
                ()->{
                    return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Customer API Falling!"));
                });

        Future<ResponseEntity<ApiResponse> > customerFuture= command.queue();
        return customerFuture.get();
//        return response.statusCode()==200 ? ResponseEntity.ok(new ApiResponse(true, "User registered successfully!"))
//                : ResponseEntity.badRequest().body(new ApiResponse(false,"User registration unsuccessfully! "));

    }























}
