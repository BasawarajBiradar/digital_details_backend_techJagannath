package com.techjagannath.digital_identification.service.homepage.impl;

import com.techjagannath.digital_identification.config.JwtUtil;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import com.techjagannath.digital_identification.service.homepage.HomePageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HomePageServiceImpl implements HomePageService {

    private final JwtUtil jwtUtil;
    private final UserMasterRepository userMasterRepository;

    public HomePageServiceImpl(JwtUtil jwtUtil, UserMasterRepository userMasterRepository) {
        this.jwtUtil = jwtUtil;
        this.userMasterRepository = userMasterRepository;
    }

    private String extractUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return jwtUtil.extractUsername(token);
    }

    @Override
    public RetrieveHomePageDetailsResultModel serviceEntryPointForRetrieveHomePageDetails(HttpServletRequest request) {
        String emailId = this.extractUser(request);
        UserMaster user = this.userMasterRepository.findByEmailId(emailId);
        // fetch what cards are present -- create a custom repository method, -- uid mapping table -- addd entry in uid tabe by creating insertin in every card
        //
        return new RetrieveHomePageDetailsResultModel(
                user.getFirstName(), user.getLastName(), user.getEmailId(), user.getMobileNumber(), user.getAlternateNumber(),
                user.getAddress().getAddressLineOne(), user.getAddress().getAddressLineTwo(), user.getAddress().getCity(),
                user.getAddress().getState(), user.getAddress().getCountry(), user.getAddress().getPinCode(), null, null, new ArrayList<>()
        );
    }
}
