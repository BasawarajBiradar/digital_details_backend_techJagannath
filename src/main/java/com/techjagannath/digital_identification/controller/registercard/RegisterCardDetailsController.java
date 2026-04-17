package com.techjagannath.digital_identification.controller.registercard;

import com.techjagannath.digital_identification.facade.registercard.RegisterCardDetailsFacade;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsResultModel;
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

    // 'kids' | 'senior' | 'business' | 'vehicle' | 'pets' | 'social';

    @PostMapping("/kids")
    private ResponseEntity<ApiResponse<RegisterCardUserKidsDetailsResultModel>> registerCardUserKidsDetails(
            @RequestBody RegisterCardUserKidsDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserKidsDetails(requestModel), "created kid profile for card user");
    }

    @PostMapping("/senior")
    private ResponseEntity<ApiResponse<RegisterCardUserSeniorDetailsResultModel>> registerCardUserSeniorDetails(
            @RequestBody RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserSeniorDetails(requestModel), "created senior profile for card user");
    }

    @PostMapping("/business")
    private ResponseEntity<ApiResponse<RegisterCardUserBusinessDetailsResultModel>> registerCardUserBusinessDetails(
            @RequestBody RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserBusinessDetails(requestModel), "created Business profile for card user");
    }

    @PostMapping("/vehicle")
    private ResponseEntity<ApiResponse<RegisterCardUserVehicleDetailsResultModel>> registerCardUserVehicleDetails(
            @RequestBody RegisterCardUserVehicleDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserVehicleDetails(requestModel), "created Vehicle profile for card user");
    }



}
