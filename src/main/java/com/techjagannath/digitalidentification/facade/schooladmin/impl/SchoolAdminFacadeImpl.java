package com.techjagannath.digitalidentification.facade.schooladmin.impl;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage.RetrieveAttendanceDetailsRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage.RetrieveAttendanceDetailsResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.attendencepiechart.SchoolAdminAttendancePieChartRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.attendencepiechart.SchoolAdminAttendancePieChartResultModelWrapper;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentbyid.RetrieveStudentByIdResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.retrieveschoollogo.SchoolLogoRetrieveResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.uploadschoollogo.SchoolLogoUploadResultModel;
import com.techjagannath.digitalidentification.service.schooladmin.SchoolAdminService;
import com.techjagannath.digitalidentification.utils.dateutils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<RetrieveStudentsListResultModel> facadeEntryPointForRetrieveStudentsList(HttpServletRequest request, RetrieveStudentsListRequestModel requestModel) {
        return this.schoolAdminService.serviceEntryPointForRetrieveStudentsList(request, requestModel);
    }

    @Override
    public RetrieveStudentByIdResultModel facadeEntryPointForRetrieveStudentById(Long id) {
        return this.schoolAdminService.serviceEntryPointForRetrieveStudentById(id);
    }

    @Override
    public SchoolLogoUploadResultModel uploadSchoolLogo(HttpServletRequest request, MultipartFile file) {
        return this.schoolAdminService.serviceEntryPointForUploadSchoolImage(request, file);
    }

    @Override
    public SchoolLogoRetrieveResultModel retrieveSchoolLogo(HttpServletRequest request) {
        return this.schoolAdminService.serviceEntryPointForRetrieveSchoolLogo(request);
    }

    @Override
    public SchoolAdminAttendancePieChartResultModelWrapper retrieveAttendancePieChartData(HttpServletRequest request, SchoolAdminAttendancePieChartRequestModel requestModel) {
        return this.schoolAdminService.serviceEntryPointForRetrieveAttendancePieChartData(request, requestModel);
    }

    @Override
    public List<RetrieveAttendanceDetailsResultModel> facadeEntryPointForRetrieveAttendanceDetailsPage(HttpServletRequest request, RetrieveAttendanceDetailsRequestModel requestModel) {
        this.validateAttendanceDataRequestModel(requestModel);
        return this.schoolAdminService.serviceEntryPointForRetrieveAttendanceDetailsPage(request, requestModel);
    }

    private void validateAttendanceDataRequestModel(RetrieveAttendanceDetailsRequestModel requestModel) {
        requestModel.setParsedFromDate(DateUtils.parseDate(requestModel.getFromDate()));
        requestModel.setParsedToDate(DateUtils.parseDate(requestModel.getToDate()));
        DateUtils.validateDateRange(requestModel.getParsedFromDate(), requestModel.getParsedToDate());
        if (requestModel.getParsedToDate().isAfter(LocalDate.now()))
            throw new ValidationException("To date cannot be after today's date");
        if (requestModel.getParsedFromDate().isBefore(LocalDate.now().minusYears(2)))
            throw new ValidationException("From date cannot be before 2 years");
    }
}
