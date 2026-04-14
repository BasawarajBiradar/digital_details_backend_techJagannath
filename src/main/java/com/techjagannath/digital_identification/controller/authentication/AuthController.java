package com.techjagannath.digital_identification.controller.authentication;

import com.techjagannath.digital_identification.config.JwtUtil;
import com.techjagannath.digital_identification.models.auth.AuthRequest;
import com.techjagannath.digital_identification.models.auth.AuthResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResultModel>> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailId(),
                        request.getPassword()
                )
        );
        return ResponseBuilder.success(new AuthResultModel(jwtUtil.generateToken(request.getEmailId())), "Success");
    }
}