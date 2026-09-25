package com.techjagannath.digitalidentification.controller.student;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.models.student.attendancepage.calendarview.GetStudentAttendancePageCalendarViewRequestModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.calendarview.GetStudentAttendancePageCalendarViewResultModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.overview.GetStudentAttendancePageOverviewRequestModel;
import com.techjagannath.digitalidentification.models.student.attendancepage.overview.GetStudentAttendancePageOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.getstudentattendance.GetStudentAttendanceDataRequestModel;
import com.techjagannath.digitalidentification.models.student.getstudentattendance.GetStudentAttendanceDataResponseModel;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.overview.GetStudentHomeworkOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.table.GetStudentHomeworkTableResultModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus.GetStudentHomeworkUpdateStatusRequestModel;
import com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus.GetStudentHomeworkUpdateStatusResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapDetailsRequestModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.recordnfctap.RecordNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidRequestModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidResultModel;
import com.techjagannath.digitalidentification.models.student.retrieveschoollist.RetrieveSchoolListResultModel;
import com.techjagannath.digitalidentification.models.student.tapphotopage.overview.GetStudentTapPhotoPageOverviewRequestModel;
import com.techjagannath.digitalidentification.models.student.tapphotopage.overview.GetStudentTapPhotoPageOverviewResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.todayupdates.RetrieveStudentHomePageTodayUpdatesResultModel;
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

    @GetMapping("/home-page/today-updates")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<RetrieveStudentHomePageTodayUpdatesResultModel>> retrieveStudentHomePageTodayUpdates(HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomePageTodayUpdates(request), "Success");
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

    @PostMapping("/attendance-history")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<List<GetStudentAttendanceDataResponseModel>>> getStudentAttendanceData(
            @RequestBody GetStudentAttendanceDataRequestModel requestModel, HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveAttendanceData(request, requestModel), "Success");
    }

    @PostMapping("/attendance-page/overview")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<GetStudentAttendancePageOverviewResultModel>> getStudentAttendancePageOverview(
            @RequestBody GetStudentAttendancePageOverviewRequestModel requestModel, HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveAttendancePageOverviewData(request, requestModel), "Success");
    }

    @PostMapping("/attendance-page/calendar-view")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<List<GetStudentAttendancePageCalendarViewResultModel>>> getStudentAttendancePageOverview(
            @RequestBody GetStudentAttendancePageCalendarViewRequestModel requestModel, HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveAttendancePageCalendarViewData(request, requestModel), "Success");
    }

    @PostMapping("/tap-photo-page/overview")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<List<GetStudentTapPhotoPageOverviewResultModel>>> getStudentTapPhotoPageOverview(
            @RequestBody GetStudentTapPhotoPageOverviewRequestModel requestModel, HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveTapPhotoPageOverviewData(request, requestModel), "Success");
    }

    @GetMapping("/homework-page/overview")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<GetStudentHomeworkOverviewResultModel>> getStudentHomeworkOverview(HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomeworkOverviewData(request), "Success");
    }

    @GetMapping("/homework-page/table")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<List<GetStudentHomeworkTableResultModel>>> getStudentHomeworkTable(HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomeworkTableData(request), "Success");
    }

    @PostMapping("/homework-page/update-status")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<ApiResponse<GetStudentHomeworkUpdateStatusResultModel>> getStudentHomeworkUpdateStatus(
            @RequestBody GetStudentHomeworkUpdateStatusRequestModel requestModel, HttpServletRequest request) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveHomeworkUpdateStatus(requestModel, request), "Success");
    }

}
