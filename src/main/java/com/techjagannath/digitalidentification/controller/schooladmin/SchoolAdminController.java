package com.techjagannath.digitalidentification.controller.schooladmin;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminController {

    private final SchoolAdminFacade schoolAdminFacade;

    public SchoolAdminController(SchoolAdminFacade schoolAdminFacade) {
        this.schoolAdminFacade = schoolAdminFacade;
    }

    public ResponseEntity<ApiResponse<AddStudentBySchoolAdminResultModel>> addStudentBySchoolAdmin(
            HttpServletRequest request, @RequestBody AddStudentBySchoolAdminRequestModel requestModel) {
        return ResponseBuilder.success(this.schoolAdminFacade.facadeEntryPointForAddStudentBySchoolAdmin(request, requestModel), "Success");
    }

}
