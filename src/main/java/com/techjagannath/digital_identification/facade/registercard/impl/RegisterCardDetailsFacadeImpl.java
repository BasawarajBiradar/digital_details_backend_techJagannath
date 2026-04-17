package com.techjagannath.digital_identification.facade.registercard.impl;

import com.techjagannath.digital_identification.facade.registercard.RegisterCardDetailsFacade;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsResultModel;
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
    public RegisterCardUserKidsDetailsResultModel facadeEntryPointForRegisterCardUserKidsDetails(
            RegisterCardUserKidsDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserKidsDetails(requestModel);
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel facadeEntryPointForRegisterCardUserSeniorDetails(
            RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserSeniorDetails(requestModel);
    }

    @Override
    public RegisterCardUserBusinessDetailsResultModel facadeEntryPointForRegisterCardUserBusinessDetails(
            RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserBusinessDetails(requestModel);
    }

    @Override
    public RegisterCardUserVehicleDetailsResultModel facadeEntryPointForRegisterCardUserVehicleDetails(
            RegisterCardUserVehicleDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserVehicleDetails(requestModel);
    }

    @Override
    public RegisterCardUserPetsDetailsResultModel facadeEntryPointForRegisterCardUserPetsDetails(
            RegisterCardUserPetsDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserPetDetails(requestModel);
    }

    @Override
    public RegisterCardUserSocialDetailsResultModel facadeEntryPointForRegisterCardUserSocialDetails(
            RegisterCardUserSocialDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserSocialDetails(requestModel);
    }
}
