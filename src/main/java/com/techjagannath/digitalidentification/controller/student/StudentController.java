package com.techjagannath.digitalidentification.controller.student;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/uid/{uid}")
    public ResponseEntity<ApiResponse<RetrieveStudentNfcTapResultModel>> retrieveStudentNfcTapDetails(@PathVariable("uid") String uid) {
        return ResponseBuilder.success(this.studentFacade.facadeEntryPointForRetrieveStudentNfcTapDetails(uid), "Success");
    }

}
