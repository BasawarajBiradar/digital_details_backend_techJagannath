package com.techjagannath.digitalidentification.service.teacher;

import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;

public interface TeacherService {

    VerifyNfcUidResultModel serviceEntryPointForVerifyTeacherNfcUid(String uid);

    RegisterTeacherUidResultModel serviceEntryPointForRegisterTeacherNfcUid(String uid, RegisterTeacherUidRequestModel requestModel);
}
