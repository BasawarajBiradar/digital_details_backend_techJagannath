package com.techjagannath.digital_identification.facade.usermanagement.impl;

import com.techjagannath.digital_identification.facade.usermanagement.UserManagementFacade;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.models.usermanagement.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsResultModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.service.usermanagement.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Override
    public SaveUserChildProfileDetailsResultModel facadeEntryPointForSaveUserChildProfileDetails(HttpServletRequest request, SaveUserChildProfileDetailsRequestModel requestModel) {
        return this.userManagementService.serviceEntryPointForSaveUserChildProfileDetails(request, requestModel);
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel facadeEntryPointForSaveUserSeniorProfileDetails(HttpServletRequest request, RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return this.userManagementService.serviceEntryPointForSaveSeniorProfileDetails(request, requestModel);
    }

    @Override
    public RegisterCardUserBusinessDetailsResultModel facadeEntryPointForSaveUserBusinessProfileDetails(HttpServletRequest request, RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return this.userManagementService.serviceEntryPointForSaveUserBusinessProfileDetails(request, requestModel);
    }
}
