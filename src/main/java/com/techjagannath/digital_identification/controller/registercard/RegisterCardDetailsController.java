package com.techjagannath.digital_identification.controller.registercard;

import com.techjagannath.digital_identification.facade.registercard.RegisterCardDetailsFacade;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register-card")
public class RegisterCardDetailsController {

    private RegisterCardDetailsFacade registerCardDetailsFacade;

    public RegisterCardDetailsController(RegisterCardDetailsFacade registerCardDetailsFacade) {
        this.registerCardDetailsFacade = registerCardDetailsFacade;
    }

    @PostMapping("/kids")
    private ResponseEntity<ApiResponse<RegisterCardUserKidsDetailsResultModel>> registerCardUserKidsDetails(@RequestBody RegisterCardUserKidsDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade.facadeEntryPointForRegisterCardUserKidsDetails(requestModel), "created kid profile for card user");
    }

}
