package com.techjagannath.digitalidentification.facade.student.impl;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapDetailsRequestModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidRequestModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.service.student.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;

    public StudentFacadeImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public RetrieveStudentHomePageInfoCardDetailsResultModel facadeEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        return this.studentService.serviceEntryPointForRetrieveHomePageInfoCardDetails(request);
    }

    @Override
    public List<RetrieveStudentHomePageTodayEntriesResultModel> facadeEntryPointForRetrieveHomePageTodayEntries(HttpServletRequest request) {
        return this.studentService.serviceEntryPointForRetrieveHomePageTodayEntries(request);
    }

    @Override
    public RetrieveStudentNfcTapResultModel facadeEntryPointForRetrieveStudentNfcTapDetails(String uid, RetrieveStudentNfcTapDetailsRequestModel requestModel) {
        return this.studentService.serviceEntryPointForRetrieveStudentNfcTapDetails(uid, requestModel);
    }

    @Override
    public VerifyNfcUidResultModel facadeEntryPointForVerifyStudentNfcUid(String uid) {
        return this.studentService.serviceEntryPointForVerifyStudentNfcUid(uid);
    }

    @Override
    public RegisterStudentUidResultModel facadeEntryPointForRegisterStudentNfcUid(String uid, RegisterStudentUidRequestModel requestModel) {
        return this.studentService.serviceEntryPointForRegisterStudentNfcUid(uid, requestModel);
    }

}
