package com.techjagannath.digitalidentification.controller.teacher;

import com.techjagannath.digitalidentification.facade.teacher.TeacherFacade;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.inforcard.RetrieveTeacherHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.revivewrequest.RetrieveTeacherHomeworkReviewRequestDetailsResultModel;
import com.techjagannath.digitalidentification.models.teacher.homeworkpage.overviewcards.RetrieveTeacherHomeworkOverviewCardsResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/uid/register/{uid}")
    public ResponseEntity<ApiResponse<RegisterTeacherUidResultModel>> registerStudentNfcUid(@PathVariable("uid") String uid
            , @RequestBody RegisterTeacherUidRequestModel requestModel) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForRegisterTeacherNfcUid(uid, requestModel), "Success");
    }

    @PostMapping("/add/homework")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<ApiResponse<TeacherAddHomeworkResultModel>> addHomework(HttpServletRequest request
            , @RequestBody TeacherAddHomeworkRequestModel requestModel) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForAddHomework(request, requestModel), "Success");
    }

    @GetMapping("/home-page/info-card")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<ApiResponse<RetrieveTeacherHomePageInfoCardDetailsResultModel>> retrieveStudentHomePageInfoCardDetails(HttpServletRequest request) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForRetrieveHomePageInfoCardDetails(request), "Success");
    }

    @GetMapping("/homework/overview_cards")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<ApiResponse<RetrieveTeacherHomeworkOverviewCardsResultModel>> retrieveHomePageOverviewCards(HttpServletRequest request) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForHomeworkPageOverviewCards(request), "Success");
    }

    @GetMapping("/homework/review_request/details")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<ApiResponse<List<RetrieveTeacherHomeworkReviewRequestDetailsResultModel>>> retrieveHomeworkReviewRequestDetails(HttpServletRequest request) {
        return ResponseBuilder.success(this.teacherFacade.facadeEntryPointForHomeworkReviewRequestDetails(request), "Success");
    }
}
