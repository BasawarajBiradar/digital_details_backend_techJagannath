package com.techjagannath.digitalidentification.facade.teacher;

import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;

public interface TeacherFacade {

    VerifyNfcUidResultModel facadeEntryPointForVerifyTeacherNfcUid(String uid);
}
