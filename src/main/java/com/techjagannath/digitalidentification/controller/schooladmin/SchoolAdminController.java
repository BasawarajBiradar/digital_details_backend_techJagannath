package com.techjagannath.digitalidentification.controller.schooladmin;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentbyid.RetrieveStudentByIdResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.uploadschoollogo.SchoolLogoUploadResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminController {

    private final SchoolAdminFacade schoolAdminFacade;

    public SchoolAdminController(SchoolAdminFacade schoolAdminFacade) {
        this.schoolAdminFacade = schoolAdminFacade;
    }

    @PostMapping("/add-student")
    @PreAuthorize("hasAuthority('SCHOOL_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<AddStudentBySchoolAdminResultModel>> addStudentBySchoolAdmin(
            HttpServletRequest request, @RequestBody AddStudentBySchoolAdminRequestModel requestModel) {
        return ResponseBuilder.success(this.schoolAdminFacade.facadeEntryPointForAddStudentBySchoolAdmin(request, requestModel), "Success");
    }

    @PostMapping("/dashboard/students")
    @PreAuthorize("hasAuthority('SCHOOL_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<List<RetrieveStudentsListResultModel>>> retrieveStudentsList(
            HttpServletRequest request,@RequestBody RetrieveStudentsListRequestModel requestModel) {
        return ResponseBuilder.success(this.schoolAdminFacade.facadeEntryPointForRetrieveStudentsList(request, requestModel), "Success");
    }

    @GetMapping("/dashboard/student/{id}")
    @PreAuthorize("hasAuthority('SCHOOL_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<RetrieveStudentByIdResultModel>> retrieveStudentById(@PathVariable("id") Long id) {
        return ResponseBuilder.success(this.schoolAdminFacade.facadeEntryPointForRetrieveStudentById(id), "Success");
    }


    @PostMapping("/upload/school-logo")
    @PreAuthorize("hasAuthority('SCHOOL_ADMIN_WRITE')")
    public ResponseEntity<ApiResponse<SchoolLogoUploadResultModel>> uploadSchoolLogo(HttpServletRequest request
            , @RequestParam("file") MultipartFile file) {
        return ResponseBuilder.success(this.schoolAdminFacade.uploadSchoolLogo(request, file), "Success");
    }

}
