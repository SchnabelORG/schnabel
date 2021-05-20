package com.schnabel.schnabel.auth.dto;

import lombok.Data;

/**
 * Login Request
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
