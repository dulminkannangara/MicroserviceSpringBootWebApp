package com.dk.app.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTPRequest {
    private String email;
    private String otp;
}
