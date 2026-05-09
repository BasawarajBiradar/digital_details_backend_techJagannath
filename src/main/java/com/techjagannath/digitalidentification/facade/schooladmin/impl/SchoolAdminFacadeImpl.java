package com.techjagannath.digitalidentification.facade.schooladmin.impl;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminResultModel;
import com.techjagannath.digitalidentification.service.schooladmin.SchoolAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class SchoolAdminFacadeImpl implements SchoolAdminFacade {

    private final SchoolAdminService schoolAdminService;

    public SchoolAdminFacadeImpl(SchoolAdminService schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }


    @Override
    public AddStudentBySchoolAdminResultModel facadeEntryPointForAddStudentBySchoolAdmin(HttpServletRequest request, AddStudentBySchoolAdminRequestModel requestModel) {
        return this.schoolAdminService.serviceEntryPointForAddStudentBySchoolAdmin(request, requestModel);
    }
}
