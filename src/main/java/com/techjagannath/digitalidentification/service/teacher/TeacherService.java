package com.techjagannath.digitalidentification.service.teacher;

import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;

public interface TeacherService {

    VerifyNfcUidResultModel serviceEntryPointForVerifyTeacherNfcUid(String uid);
}
