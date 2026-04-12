package com.techjagannath.digital_identification.service.usermanagement;


import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;

public interface UserManagementService {

    RegisterUserResultModel serviceEntryPointForRegisterUser(RegisterUserRequestModel requestModel);
}
