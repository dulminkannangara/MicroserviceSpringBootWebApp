package com.dk.app.service.register;

import com.dk.app.dto.ApiResponse;
import com.dk.app.dto.OTPRequest;
import com.dk.app.dto.SignUpForm;
import com.dk.app.model.Role;
import com.dk.app.model.RoleName;
import com.dk.app.model.User;
import com.dk.app.repository.RoleRepository;
import com.dk.app.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public UserService(){
        objectMapper = new ObjectMapper();
    }

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<ApiResponse> registerUser(SignUpForm signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Fail -> Email is already in use!"));
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
                    roles.add(adminRole);

                    break;
                case "student":
                    Role therapistRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
                    roles.add(therapistRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        user.activate();
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!"));
    }

    public String sendOTP(SignUpForm payload) throws Exception {

        // send otp to mobile
        return "1234";
    }

    public OTPRequest getOTPPayload(LinkedHashMap<String, String> payload_values){
            for(Map.Entry entryt : payload_values.entrySet()){
                System.out.println(entryt.getKey()+" : "+entryt.getValue()+" : "+entryt.getValue().getClass());
            }
        OTPRequest payload = OTPRequest.builder()
                .email(payload_values.get("email"))
                .otp(payload_values.get("otp"))
                .build();
        if(payload.getEmail()==null || payload.getEmail().isEmpty()){
            throw new RuntimeException("Email is missing!");
        }else if(payload.getOtp()==null || payload.getOtp().isEmpty()){
            throw new RuntimeException("OTP is missing!");
        }
        return payload;

    }

    public SignUpForm getPayload(LinkedHashMap<String, Object> payload_values){
        SignUpForm  payload = null;
            for(Map.Entry entryt : payload_values.entrySet()){
                System.out.println(entryt.getKey()+" : "+entryt.getValue().getClass());
            }
            ArrayList<String> roleStr = (ArrayList<String>) payload_values.get("role");
            Set<String> roles = new HashSet<>();
        roleStr.forEach(role -> {
            switch(role) {
                case "admin":
                    roles.add("admin");
                    break;
                case "student":
                    roles.add("student");
                    break;
                default:
                    roles.add("employee");
            }
        });


            payload = SignUpForm.builder()
                    .name(payload_values.get("name").toString())
                    .email(payload_values.get("email").toString())
                    .role(roles)
                    .password(payload_values.get("password").toString())
                    .build();
            if(payload.getPassword()==null || payload.getPassword().isEmpty() ) {
                throw new RuntimeException("Password is missing!");
            }else if(payload.getName()==null || payload.getName().isEmpty()){
                throw new RuntimeException("Name is missing!");
            }else if(payload.getEmail()==null || payload.getEmail().isEmpty()){
                throw new RuntimeException("Email is missing!");
            }else if(payload.getRole()==null || payload.getRole().isEmpty()){
                throw new RuntimeException("Role is missing!");
            }

        return payload;
    }
}
