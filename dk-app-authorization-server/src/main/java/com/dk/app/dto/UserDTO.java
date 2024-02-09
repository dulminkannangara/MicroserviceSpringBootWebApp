package com.dk.app.dto;

import com.dk.app.models.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;
    private String username;

    public static UserDTO from(User user) {
        return builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .build();
    }
}
