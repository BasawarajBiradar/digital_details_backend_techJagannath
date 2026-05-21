package com.techjagannath.digitalidentification.controller.authentication;

import com.techjagannath.digitalidentification.config.JwtUtil;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.models.auth.AuthRequest;
import com.techjagannath.digitalidentification.models.auth.AuthResultModel;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserMasterRepository userMasterRepository;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          UserMasterRepository userMasterRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userMasterRepository = userMasterRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResultModel>> login(@RequestBody AuthRequest request) {
        UserMaster user = userMasterRepository.findByEmailId(request.getEmailId());

        if (user == null) {return ResponseBuilder.error("Invalid email id", "401",HttpStatus.NOT_FOUND);
        }

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmailId(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException ex) {
            return ResponseBuilder.error("Wrong password", "401", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(request.getEmailId());

        return ResponseBuilder.success(
                new AuthResultModel(token, user.getRole().getRole()), "Success");
    }
}