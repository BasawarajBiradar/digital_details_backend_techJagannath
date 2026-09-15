package com.techjagannath.digitalidentification.service.teacher;

import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import jakarta.servlet.http.HttpServletRequest;

public interface TeacherService {

    VerifyNfcUidResultModel serviceEntryPointForVerifyTeacherNfcUid(String uid);

    RegisterTeacherUidResultModel serviceEntryPointForRegisterTeacherNfcUid(String uid, RegisterTeacherUidRequestModel requestModel);

    TeacherAddHomeworkResultModel serviceEntryPointForAddHomework(HttpServletRequest request, TeacherAddHomeworkRequestModel requestModel);
}
