package com.dk.app.controller;

import com.dk.app.dto.SignUpForm;
import com.dk.app.model.Role;
import com.dk.app.model.RoleName;
import com.dk.app.model.User;
import com.dk.app.repository.RoleRepository;
import com.dk.app.repository.UserRepository;
import com.dk.app.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/customer/auth")
public class CustomerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
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
                case "therapist":
                    Role therapistRole = roleRepository.findByName(RoleName.ROLE_THERAPIST)
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
}
