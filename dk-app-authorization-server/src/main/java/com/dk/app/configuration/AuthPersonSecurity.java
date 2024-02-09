package com.dk.app.configuration;

import com.dk.app.models.User;
import org.springframework.stereotype.Component;

@Component
public class AuthPersonSecurity {
    public boolean hasPermission(User user, Integer id) {
        // do whatever checks you want here
        return user.getId()==id;
    }
}
