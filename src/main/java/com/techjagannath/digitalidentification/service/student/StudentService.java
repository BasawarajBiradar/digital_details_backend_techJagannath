package com.techjagannath.digitalidentification.service.student;

import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface StudentService {
    RetrieveStudentHomePageInfoCardDetailsResultModel serviceEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request);

    List<RetrieveStudentHomePageTodayEntriesResultModel> serviceEntryPointForRetrieveHomePageTodayEntries(HttpServletRequest request);

    RetrieveStudentNfcTapResultModel serviceEntryPointForRetrieveStudentNfcTapDetails(String uid);

    VerifyNfcUidResultModel serviceEntryPointForVerifyStudentNfcUid(String uid);
}
