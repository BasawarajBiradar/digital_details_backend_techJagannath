package com.techjagannath.digital_identification.controller.usermanagement;

import com.techjagannath.digital_identification.facade.usermanagement.UserManagementFacade;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/user-management")
@CrossOrigin
@RestController
public class UserManagementController {

    private final UserManagementFacade userManagementFacade;

    public UserManagementController(UserManagementFacade userManagementFacade) {
        this.userManagementFacade = userManagementFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserResultModel>> registerUser(@Valid @RequestBody RegisterUserRequestModel requestModel) {
        return ResponseBuilder.created(this.userManagementFacade.facadeEntryPointForRegisterUser(requestModel), "New user registered");
    }
}
