package com.techjagannath.digital_identification.service.homepage;

import com.techjagannath.digital_identification.models.homePage.retrieve.RetrieveHomePageDetailsResultModel;
import jakarta.servlet.http.HttpServletRequest;

public interface HomePageService {
    RetrieveHomePageDetailsResultModel serviceEntryPointForRetrieveHomePageDetails(HttpServletRequest request);
}
