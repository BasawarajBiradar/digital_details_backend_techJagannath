package com.techjagannath.digitalidentification.models.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String emailId;
    private String password;
}
