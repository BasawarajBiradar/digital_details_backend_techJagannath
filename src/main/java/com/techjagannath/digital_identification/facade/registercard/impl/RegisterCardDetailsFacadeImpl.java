package com.techjagannath.digital_identification.facade.registercard.impl;

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
import com.techjagannath.digital_identification.service.registercard.RegisterCardDetailsService;
import org.springframework.stereotype.Component;

@Component
public class RegisterCardDetailsFacadeImpl implements RegisterCardDetailsFacade {

    private final RegisterCardDetailsService registerCardDetailsService;

    public RegisterCardDetailsFacadeImpl(RegisterCardDetailsService registerCardDetailsService) {
        this.registerCardDetailsService = registerCardDetailsService;
    }

    @Override
    public RegisterCardUserKidsDetailsResultModel facadeEntryPointForRegisterCardUserKidsDetails(String uid,
                                                                                                 RegisterCardUserKidsDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserKidsDetails(uid, requestModel);
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel facadeEntryPointForRegisterCardUserSeniorDetails(String uid,
                                                                                                     RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserSeniorDetails(uid, requestModel);
    }

    @Override
    public RegisterCardUserBusinessDetailsResultModel facadeEntryPointForRegisterCardUserBusinessDetails(String uid,
                                                                                                         RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserBusinessDetails(uid, requestModel);
    }

    @Override
    public RegisterCardUserVehicleDetailsResultModel facadeEntryPointForRegisterCardUserVehicleDetails(String uid,
                                                                                                       RegisterCardUserVehicleDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserVehicleDetails(uid, requestModel);
    }

    @Override
    public RegisterCardUserPetsDetailsResultModel facadeEntryPointForRegisterCardUserPetsDetails(String uid,
                                                                                                 RegisterCardUserPetsDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserPetDetails(uid, requestModel);
    }

    @Override
    public RegisterCardUserSocialDetailsResultModel facadeEntryPointForRegisterCardUserSocialDetails(String uid,
                                                                                                     RegisterCardUserSocialDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserSocialDetails(uid, requestModel);
    }

    @Override
    public RetrieveUserCardDetailsResultModel facadeEntryPointForRetrieveUserCardDetails(RetrieveUserCardDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRetrieveUserDetails(requestModel);
    }
}
