package com.dk.app.controllers;

import com.dk.app.dto.UserDTO;
import com.dk.app.models.User;
import com.dk.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
//    @PreAuthorize("#user.id == #id")
    @PreAuthorize("@authPersonSecurity.hasPermission(#user,#id)")
    public ResponseEntity user(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(UserDTO.from(userRepository.findById(Integer.parseInt(id)).orElseThrow()));
    }
}
