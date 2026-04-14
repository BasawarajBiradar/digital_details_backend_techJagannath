package com.techjagannath.digital_identification.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequest {
    private String emailId;
    private String password;
}
