package com.techjagannath.digital_identification.facade.homepage.impl;

import com.techjagannath.digital_identification.facade.homepage.HomePageFacade;
import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import com.techjagannath.digital_identification.service.homepage.HomePageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HomePageFacadeImpl implements HomePageFacade {

    HomePageService homePageService;

    public HomePageFacadeImpl(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @Override
    public RetrieveHomePageDetailsResultModel facadeEntryPointForRetrieveHomePageDetails(HttpServletRequest request) {
        return this.homePageService.serviceEntryPointForRetrieveHomePageDetails(request);
    }

}
