package com.Jupiter.hrm.security;

import lombok.Data;
import lombok.Getter;

@Getter
public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters and setters
}


