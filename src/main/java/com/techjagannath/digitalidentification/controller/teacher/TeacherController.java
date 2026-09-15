package com.techjagannath.digitalidentification.controller.teacher;

import com.techjagannath.digitalidentification.facade.teacher.TeacherFacade;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherFacade teacherFacade;

    public TeacherController(TeacherFacade teacherFacade) {
        this.teacherFacade = teacherFacade;
    }

    @GetMapping("/uid/verify/{uid}")
    public ResponseEntity<ApiResponse<VerifyNfcUidResultModel>> verifyStudentNfcUid(@PathVariable("uid") String uid) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForVerifyTeacherNfcUid(uid), "Success");
    }

    /**
    @PostMapping("/uid/register/{uid}")
    public ResponseEntity<ApiResponse<RegisterStudentUidResultModel>> registerStudentNfcUid(@PathVariable("uid") String uid
            , @RequestBody RegisterStudentUidRequestModel requestModel) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRegisterStudentNfcUid(uid, requestModel), "Success");
    }

    @PostMapping(value = "/uid/record-tap")
    public ResponseEntity<ApiResponse<RecordNfcTapResultModel>> registerStudentNfcUid(
            @RequestParam String uid, @RequestParam Long deviceId, @RequestParam("image") MultipartFile image) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRecordNfcTap(uid, deviceId, image), "Success");
    }
            */

}
