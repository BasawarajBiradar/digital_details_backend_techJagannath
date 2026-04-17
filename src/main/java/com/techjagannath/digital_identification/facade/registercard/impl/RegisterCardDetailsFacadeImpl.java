package com.techjagannath.digital_identification.facade.registercard.impl;

import com.techjagannath.digital_identification.facade.registercard.RegisterCardDetailsFacade;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.service.registercard.RegisterCardDetailsService;
import org.springframework.stereotype.Component;

@Component
public class RegisterCardDetailsFacadeImpl implements RegisterCardDetailsFacade {

    private final RegisterCardDetailsService registerCardDetailsService;

    public RegisterCardDetailsFacadeImpl(RegisterCardDetailsService registerCardDetailsService) {
        this.registerCardDetailsService = registerCardDetailsService;
    }

    @Override
    public RegisterCardUserKidsDetailsResultModel facadeEntryPointForRegisterCardUserKidsDetails(RegisterCardUserKidsDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserKidsDetails(requestModel);
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel facadeEntryPointForRegisterCardUserSeniorDetails(RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return this.registerCardDetailsService.serviceEntryPointForRegisterCardUserSeniorDetails(requestModel);
    }
}
