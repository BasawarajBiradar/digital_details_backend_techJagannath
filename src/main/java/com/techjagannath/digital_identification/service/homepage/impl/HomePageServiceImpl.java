package com.techjagannath.digital_identification.service.homepage.impl;

import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import com.techjagannath.digital_identification.service.homepage.HomePageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class HomePageServiceImpl implements HomePageService {


    @Override
    public RetrieveHomePageDetailsResultModel serviceEntryPointForRetrieveHomePageDetails(HttpServletRequest request) {
        // extract user
        // fetch user detials
        // fetch what cards are present -- create a custom repository method, -- uid mapping table -- addd entry in uid tabe by creating insertin in every card
        //
        return null;
    }
}
