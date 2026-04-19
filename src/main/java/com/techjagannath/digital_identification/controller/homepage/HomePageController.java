package com.techjagannath.digital_identification.controller.homepage;

import com.techjagannath.digital_identification.facade.homepage.HomePageFacade;
import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomePageController {

    private final HomePageFacade homePageFacade;

    public HomePageController(HomePageFacade homePageFacade) {
        this.homePageFacade = homePageFacade;
    }

    @GetMapping("/retrieve")
    public ResponseEntity<ApiResponse<RetrieveHomePageDetailsResultModel>> retrieveHomePageDetails(HttpServletRequest request) {
        return ResponseBuilder.success(this.homePageFacade.facadeEntryPointForRetrieveHomePageDetails(request), "Success");
    }
}
