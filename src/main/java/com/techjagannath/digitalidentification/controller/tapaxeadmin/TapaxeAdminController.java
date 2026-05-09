package com.techjagannath.digitalidentification.controller.tapaxeadmin;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ApiResponse<AddSchoolAdminResultModel>> addSchoolAdmin(
            HttpServletRequest request, @RequestBody AddSchoolAdminRequestModel requestModel) {
        return ResponseBuilder.success(this.tapaxeAdminFacade.facadeEntryPointForAddSchoolAdmin(request, requestModel), "Success");
    }
}
