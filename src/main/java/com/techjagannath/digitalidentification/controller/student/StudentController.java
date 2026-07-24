package com.techjagannath.digitalidentification.controller.student;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapDetailsRequestModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.recordnfctap.RecordNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidRequestModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidResultModel;
import com.techjagannath.digitalidentification.models.student.retrieveschoollist.RetrieveSchoolListResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.uploadprofilephoto.StudentProfilePhotoUploadResultModel;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import com.techjagannath.digitalidentification.utils.qrgenerator.QRCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentFacade studentFacade;

    public StudentController(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    @GetMapping("/home-page/info-card")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<RetrieveStudentHomePageInfoCardDetailsResultModel>> retrieveStudentHomePageInfoCardDetails(HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomePageInfoCardDetails(request), "Success");
    }

    @GetMapping("/home-page/today-entries")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<List<RetrieveStudentHomePageTodayEntriesResultModel>>> retrieveStudentHomePageTodayEntries(HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomePageTodayEntries(request), "Success");
    }

    @PostMapping("/uid/{uid}")
    public ResponseEntity<ApiResponse<RetrieveStudentNfcTapResultModel>> retrieveStudentNfcTapDetails(@PathVariable("uid") String uid
            ,@RequestBody RetrieveStudentNfcTapDetailsRequestModel requestModel) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveStudentNfcTapDetails(uid, requestModel)
                , "Success");
    }

    @GetMapping(value = "/qr-generate", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQR(@RequestParam String url) throws Exception {
        return QRCodeGenerator.generateQRCode(url, 250, 250);
    }

    @GetMapping("/uid/verify/{uid}")
    public ResponseEntity<ApiResponse<VerifyNfcUidResultModel>> verifyStudentNfcUid(@PathVariable("uid") String uid) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForVerifyStudentNfcUid(uid), "Success");
    }

    @PostMapping("/uid/register/{uid}")
    public ResponseEntity<ApiResponse<RegisterStudentUidResultModel>> registerStudentNfcUid(@PathVariable("uid") String uid
            ,@RequestBody RegisterStudentUidRequestModel requestModel) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRegisterStudentNfcUid(uid, requestModel), "Success");
    }

    @GetMapping("/uid/school-list")
    public ResponseEntity<ApiResponse<List<RetrieveSchoolListResultModel>>> retrieveSchoolList() {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveSchoolList(), "Success");
    }

    @PostMapping("/upload/profile-photo")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<StudentProfilePhotoUploadResultModel>> uploadStudentProfileImage(HttpServletRequest request
            , @RequestParam("file") MultipartFile file) {
        return ResponseBuilder.success(studentFacade.uploadStudentProfileImage(request, file), "Success");
    }

    @PostMapping(value = "/uid/record-tap")
    public ResponseEntity<ApiResponse<RecordNfcTapResultModel>> registerStudentNfcUid(
            @RequestParam String uid, @RequestParam Long deviceId, @RequestParam("image") MultipartFile image) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRecordNfcTap(uid, deviceId, image), "Success");
    }

}
