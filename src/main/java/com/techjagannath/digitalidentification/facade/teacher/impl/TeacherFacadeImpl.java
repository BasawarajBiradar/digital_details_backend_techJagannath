package com.techjagannath.digitalidentification.facade.teacher.impl;

import com.techjagannath.digitalidentification.facade.teacher.TeacherFacade;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
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
}
