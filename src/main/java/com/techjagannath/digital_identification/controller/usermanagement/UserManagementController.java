package com.techjagannath.digital_identification.controller.usermanagement;

import com.techjagannath.digital_identification.facade.usermanagement.UserManagementFacade;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.models.usermanagement.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsResultModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.utils.apiresponse.ApiResponse;
import com.techjagannath.digital_identification.utils.apiresponse.ResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/save/kids")
    public ResponseEntity<ApiResponse<SaveUserChildProfileDetailsResultModel>> saveUserChildProfileDetails(HttpServletRequest request, @Valid @RequestBody SaveUserChildProfileDetailsRequestModel requestModel) {
        return ResponseBuilder.created(this.userManagementFacade.facadeEntryPointForSaveUserChildProfileDetails(request, requestModel),
                "New user registered");
    }

    @PostMapping("/save/senior")
    public ResponseEntity<ApiResponse<RegisterCardUserSeniorDetailsResultModel>> saveUserSeniorProfileDetails(HttpServletRequest request, @Valid @RequestBody RegisterCardUserSeniorDetailsRequestModel requestModel) {
        return ResponseBuilder.created(this.userManagementFacade.facadeEntryPointForSaveUserSeniorProfileDetails(request, requestModel),
                "New user registered");
    }

    @PostMapping("/save/business")
    public ResponseEntity<ApiResponse<RegisterCardUserBusinessDetailsResultModel>> saveUserBusinessProfileDetails(HttpServletRequest request, @Valid @RequestBody RegisterCardUserBusinessDetailsRequestModel requestModel) {
        return ResponseBuilder.created(this.userManagementFacade.facadeEntryPointForSaveUserBusinessProfileDetails(request, requestModel),
                "New user registered");
    }

    // senior
    // pets
    // business
    // vehicle
    // social

}
