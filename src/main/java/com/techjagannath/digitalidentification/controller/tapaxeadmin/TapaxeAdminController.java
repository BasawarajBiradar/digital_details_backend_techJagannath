package com.techjagannath.digitalidentification.controller.tapaxeadmin;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddLTapaxeAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddTapaxeAdminRequestModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tapaxe-admin")
public class TapaxeAdminController {

    private final TapaxeAdminFacade tapaxeAdminFacade;

    public TapaxeAdminController(TapaxeAdminFacade tapaxeAdminFacade) {
        this.tapaxeAdminFacade = tapaxeAdminFacade;
    }

    @PostMapping("/add/school-admin")
    @PreAuthorize("hasAuthority('TAPAXE_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<AddSchoolAdminResultModel>> addSchoolAdmin(
            HttpServletRequest request, @RequestBody AddSchoolAdminRequestModel requestModel) {
        return ResponseBuilder.success(this.tapaxeAdminFacade.facadeEntryPointForAddSchoolAdmin(request, requestModel), "Success");
    }

    @PostMapping("/add/school")
    @PreAuthorize("hasAuthority('TAPAXE_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<AddSchoolResultModel>> addSchool(
            HttpServletRequest request, @RequestBody AddSchoolRequestModel requestModel) {
        return ResponseBuilder.success(this.tapaxeAdminFacade.facadeEntryPointForAddSchool(request, requestModel), "Success");
    }

    @PostMapping("/add/admin")
    public ResponseEntity<ApiResponse<AddLTapaxeAdminResultModel>> addTapaxeAdmin(@RequestBody AddTapaxeAdminRequestModel requestModel) {
        return ResponseBuilder.success(this.tapaxeAdminFacade.facadeEntryPointForAddTapaxeAdmin(requestModel), "Success");
    }
}
