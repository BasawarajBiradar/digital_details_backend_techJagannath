package com.techjagannath.digitalidentification.facade.teacher.impl;

import com.techjagannath.digitalidentification.facade.teacher.TeacherFacade;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.inforcard.RetrieveTeacherHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.reviewrequestupdatestatus.RetrieveTeacherHomeworkReviewRequestUpdateStatusRequestModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.reviewrequestupdatestatus.RetrieveTeacherHomeworkReviewRequestUpdateStatusResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.revivewrequest.RetrieveTeacherHomeworkReviewRequestDetailsResultModel;
import com.techjagannath.digitalidentification.models.teacher.homeworkpage.overviewcards.RetrieveTeacherHomeworkOverviewCardsResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
import com.techjagannath.digitalidentification.utils.dateutils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class TeacherFacadeImpl implements TeacherFacade {

    private final TeacherService teacherService;

    public TeacherFacadeImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public VerifyNfcUidResultModel facadeEntryPointForVerifyTeacherNfcUid(String uid) {
        return this.teacherService.serviceEntryPointForVerifyTeacherNfcUid(uid);
    }

    @Override
    public RegisterTeacherUidResultModel facadeEntryPointForRegisterTeacherNfcUid(String uid, RegisterTeacherUidRequestModel requestModel) {
        return this.teacherService.serviceEntryPointForRegisterTeacherNfcUid(uid, requestModel);
    }

    @Override
    public TeacherAddHomeworkResultModel facadeEntryPointForAddHomework(HttpServletRequest request, TeacherAddHomeworkRequestModel requestModel, List<MultipartFile> files) {
        requestModel.setParsedDeadlineDate(DateUtils.parseDate(requestModel.getDeadlineDate()));
        return this.teacherService.serviceEntryPointForAddHomework(request, requestModel, files);
    }

    @Override
    public RetrieveTeacherHomePageInfoCardDetailsResultModel facadeEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        return this.teacherService.serviceEntryPointForRetrieveHomePageInfoCardDetails(request);
    }

    @Override
    public RetrieveTeacherHomeworkOverviewCardsResultModel facadeEntryPointForHomeworkPageOverviewCards(HttpServletRequest request) {
        return this.teacherService.serviceEntryPointForHomeworkPageOverview(request);
    }

    @Override
    public List<RetrieveTeacherHomeworkReviewRequestDetailsResultModel> facadeEntryPointForHomeworkReviewRequestDetails(HttpServletRequest request) {
        return this.teacherService.serviceEntryPointForHomeworkReviewRequestDetails(request);
    }

    @Override
    public RetrieveTeacherHomeworkReviewRequestUpdateStatusResultModel facadeEntryPointForHomeworkReviewRequestUpdateStatus(HttpServletRequest request
            , RetrieveTeacherHomeworkReviewRequestUpdateStatusRequestModel requestModel) {
        return this.teacherService.serviceEntryPointForReviewRequestUpdateStatus(request, requestModel);
    }
}
