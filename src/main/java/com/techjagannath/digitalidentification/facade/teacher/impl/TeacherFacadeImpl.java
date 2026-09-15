package com.techjagannath.digitalidentification.facade.teacher.impl;

import com.techjagannath.digitalidentification.facade.teacher.TeacherFacade;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
import com.techjagannath.digitalidentification.utils.dateutils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

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
    public TeacherAddHomeworkResultModel facadeEntryPointForAddHomework(HttpServletRequest request, TeacherAddHomeworkRequestModel requestModel) {
        requestModel.setParsedDeadlineDate(DateUtils.parseDate(requestModel.getDeadlineDate()));
        return this.teacherService.serviceEntryPointForAddHomework(request, requestModel);
    }
}
