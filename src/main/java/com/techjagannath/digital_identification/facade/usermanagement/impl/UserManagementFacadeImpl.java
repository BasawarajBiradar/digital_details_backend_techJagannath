package com.techjagannath.digital_identification.facade.usermanagement.impl;

import com.techjagannath.digital_identification.facade.usermanagement.UserManagementFacade;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.service.usermanagement.UserManagementService;
import org.springframework.stereotype.Component;

@Component
public class UserManagementFacadeImpl implements UserManagementFacade {

    private final UserManagementService userManagementService;

    public UserManagementFacadeImpl(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @Override
    public RegisterUserResultModel facadeEntryPointForRegisterUser(RegisterUserRequestModel requestModel) {
        return this.userManagementService.serviceEntryPointForRegisterUser(requestModel);
    }
}
