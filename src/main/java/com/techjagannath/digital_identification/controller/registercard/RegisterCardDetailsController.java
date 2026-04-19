package com.techjagannath.digital_identification.controller.registercard;

import com.techjagannath.digital_identification.facade.registercard.RegisterCardDetailsFacade;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register-card")
public class RegisterCardDetailsController {

    private RegisterCardDetailsFacade registerCardDetailsFacade;

    public RegisterCardDetailsController(RegisterCardDetailsFacade registerCardDetailsFacade) {
        this.registerCardDetailsFacade = registerCardDetailsFacade;
    }

    @PostMapping("/kids/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserKidsDetailsResultModel>> registerCardUserKidsDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserKidsDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserKidsDetails(uid, requestModel), "created kid profile for card user");
    }

    @PostMapping("/senior/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserSeniorDetailsResultModel>> registerCardUserSeniorDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserSeniorDetails(uid, requestModel), "created senior profile for card user");
    }

    @PostMapping("/business/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserBusinessDetailsResultModel>> registerCardUserBusinessDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserBusinessDetails(uid, requestModel), "created Business profile for card user");
    }

    @PostMapping("/vehicle/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserVehicleDetailsResultModel>> registerCardUserVehicleDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserVehicleDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserVehicleDetails(uid, requestModel), "created Vehicle profile for card user");
    }

    @PostMapping("/pets/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserPetsDetailsResultModel>> registerCardUserPetsDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserPetsDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserPetsDetails(uid, requestModel), "created Pets profile for card user");
    }

    @PostMapping("/social/{uid}")
    public ResponseEntity<ApiResponse<RegisterCardUserSocialDetailsResultModel>> registerCardUserSocialDetails(@PathVariable("uid") String uid,
            @RequestBody RegisterCardUserSocialDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade
                .facadeEntryPointForRegisterCardUserSocialDetails(uid, requestModel), "created Social profile for card user");
    }

    @PostMapping("/retrieve")
    public ResponseEntity<ApiResponse<RetrieveUserCardDetailsResultModel>> registerCardUserSocialDetails(
            @RequestBody RetrieveUserCardDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.registerCardDetailsFacade.facadeEntryPointForRetrieveUserCardDetails(requestModel), "retireve card details");
    }



}
