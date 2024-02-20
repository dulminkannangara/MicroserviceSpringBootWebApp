package com.dk.app.service.register;

import com.dk.app.dto.SignUpForm;
import com.dk.app.model.Role;
import com.dk.app.model.RoleName;
import com.dk.app.repository.RoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

@Service
public class UserService {
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    public UserService(){
        objectMapper = new ObjectMapper();
    }


    public SignUpForm getPayload(LinkedHashMap<String, String> payload_values) throws Exception{
        SignUpForm  payload = null;
//            JSONObject jsonObj = new JSONObject(payload_values.get("role"));
            ObjectMapper mapper = new ObjectMapper();
            Set<String> roleStr = mapper.readValue(payload_values.get("role").getBytes(), HashSet.class);

            System.out.println("Roles : "+ roleStr);
//            Set<Role> roles = new HashSet<>();
//            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
//            roles.add(adminRole);
            Set<String> roles = new HashSet<>();
            roles.add("admin");

            payload = SignUpForm.builder()
                    .name(payload_values.get("name"))
                    .email(payload_values.get("email"))
                    .role(roles)
                    .password(payload_values.get("password"))
                    .build();
            if(payload.getPassword().isEmpty()) {
                throw new RuntimeException("Password is missing!");
            }else if(payload.getName().isEmpty()){
                throw new RuntimeException("Name is missing!");
            }else if(payload.getEmail().isEmpty()){
                throw new RuntimeException("Email is missing!");
            }else if(payload.getRole().isEmpty()){
                throw new RuntimeException("Role is missing!");
            }

        return payload;
    }
}
