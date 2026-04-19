package com.techjagannath.digital_identification.facade.homepage;

import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import jakarta.servlet.http.HttpServletRequest;

public interface HomePageFacade {
    RetrieveHomePageDetailsResultModel facadeEntryPointForRetrieveHomePageDetails(HttpServletRequest request);
}
