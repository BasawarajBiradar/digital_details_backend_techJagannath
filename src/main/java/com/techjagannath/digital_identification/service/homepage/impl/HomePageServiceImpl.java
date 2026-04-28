package com.techjagannath.digital_identification.service.homepage.impl;

import com.techjagannath.digital_identification.config.JwtUtil;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.UserProfileNfcMapping;
import com.techjagannath.digital_identification.models.homePage.retrieve.CardsToShowResultModel;
import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import com.techjagannath.digital_identification.repository.profiles.UserProfileNfcMappingRepository;
import com.techjagannath.digital_identification.service.homepage.HomePageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    private final JwtUtil jwtUtil;
    private final UserMasterRepository userMasterRepository;
    private final UserProfileNfcMappingRepository userProfileNfcMappingRepository;

    public HomePageServiceImpl(JwtUtil jwtUtil, UserMasterRepository userMasterRepository, UserProfileNfcMappingRepository userProfileNfcMappingRepository) {
        this.jwtUtil = jwtUtil;
        this.userMasterRepository = userMasterRepository;
        this.userProfileNfcMappingRepository = userProfileNfcMappingRepository;
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
        List<UserProfileNfcMapping> cardsResultList = this.userProfileNfcMappingRepository.findAllByUserMaster(user);
        List<CardsToShowResultModel> cards = new ArrayList<>();
        for (UserProfileNfcMapping card: cardsResultList)
            cards.add(new CardsToShowResultModel(card.getProfileType().getProfileType(), true, card.getUid()));

        return new RetrieveHomePageDetailsResultModel(
                user.getFirstName(), user.getLastName(), user.getEmailId(), user.getMobileNumber(), cards
        );
    }
}
