package com.techjagannath.digitalidentification.facade.teacher;

import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;

public interface TeacherFacade {

    VerifyNfcUidResultModel facadeEntryPointForVerifyTeacherNfcUid(String uid);

    RegisterTeacherUidResultModel facadeEntryPointForRegisterTeacherNfcUid(String uid, RegisterTeacherUidRequestModel requestModel);
}
