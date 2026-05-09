package com.techjagannath.digitalidentification.utils;

import com.techjagannath.digitalidentification.config.JwtUtil;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.exception.InvalidAuthorizationException;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CommonMethods {

    private final JwtUtil jwtUtil;
    private final UserMasterRepository userMasterRepository;

    public CommonMethods(JwtUtil jwtUtil, UserMasterRepository userMasterRepository) {
        this.jwtUtil = jwtUtil;
        this.userMasterRepository = userMasterRepository;
    }

    public UserMaster extractUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidAuthorizationException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        return this.userMasterRepository.findByEmailId(email);
    }

}
