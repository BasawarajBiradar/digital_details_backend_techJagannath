package com.techjagannath.digital_identification.facade.usermanagement;

import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;

public interface UserManagementFacade {

    RegisterUserResultModel facadeEntryPointForRegisterUser(RegisterUserRequestModel requestModel);

}
