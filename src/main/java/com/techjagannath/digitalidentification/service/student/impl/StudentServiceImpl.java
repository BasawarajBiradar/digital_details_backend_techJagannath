package com.techjagannath.digitalidentification.service.student.impl;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.StudentDetailsMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.repository.NfcCardTapsHistoryRepository;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import com.techjagannath.digitalidentification.service.student.StudentService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final CommonMethods commonMethods;
    private final NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository;
    private final UserMasterRepository userMasterRepository;
    private final String USER_NOT_FOUND = "User not found";

    public StudentServiceImpl(CommonMethods commonMethods, NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository,
                              UserMasterRepository userMasterRepository) {
        this.commonMethods = commonMethods;
        this.nfcCardTapsHistoryRepository = nfcCardTapsHistoryRepository;
        this.userMasterRepository = userMasterRepository;
    }

    @Override
    public RetrieveStudentHomePageInfoCardDetailsResultModel serviceEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(" ").append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(" ").append(user.getLastName());

        SchoolMaster school = user.getSchool();
        StudentDetailsMaster student = user.getStudentDetails();

        StringBuilder studentAddress = new StringBuilder();
        if (student.getStudentAddress().getAddressLineOne() != null)
            studentAddress.append(student.getStudentAddress().getAddressLineOne());
        if (student.getStudentAddress().getAddressLineTwo() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getAddressLineTwo());
        if (student.getStudentAddress().getCity() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCity());
        if (student.getStudentAddress().getPinCode() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getPinCode());
        if (student.getStudentAddress().getState() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getState());
        if (student.getStudentAddress().getCountry() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCountry());

        return new RetrieveStudentHomePageInfoCardDetailsResultModel(
                school.getSchoolName(), null, null, fullName.toString(), student.getClassLevel(), student.getDivision(),
                student.getBloodGroup(), user.getMobileNumber(), user.getEmailId(),
                student.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), studentAddress.toString(),
                student.getEmergencyContactName(), student.getEmergencyContactNumber(),
                student.getEmergencyContactRelation(), student.getAlternateContactNumber(), user.getUid()
        );
    }

    @Override
    public List<RetrieveStudentHomePageTodayEntriesResultModel> serviceEntryPointForRetrieveHomePageTodayEntries(HttpServletRequest request) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        UserMaster user = this.commonMethods.extractUser(request);
        List<NfcCardTapsHistory> entries = this.nfcCardTapsHistoryRepository.retrieveLoggedInUserTodayEntries(user);
        List<RetrieveStudentHomePageTodayEntriesResultModel> resultModels = new ArrayList<>();
        for (NfcCardTapsHistory entry : entries)
            resultModels.add(new RetrieveStudentHomePageTodayEntriesResultModel(
                    entry.getTimeStamp().format(dateFormatter), entry.getTimeStamp().format(timeFormatter), null));

        return resultModels;
    }

    @Override
    public RetrieveStudentNfcTapResultModel serviceEntryPointForRetrieveStudentNfcTapDetails(String uid) {
        UserMaster user = this.userMasterRepository.findByUid(uid);
        if (user == null)
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(" ").append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(" ").append(user.getLastName());

        SchoolMaster school = user.getSchool();
        StudentDetailsMaster student = user.getStudentDetails();

        StringBuilder studentAddress = new StringBuilder();
        if (student.getStudentAddress().getAddressLineOne() != null)
            studentAddress.append(student.getStudentAddress().getAddressLineOne());
        if (student.getStudentAddress().getAddressLineTwo() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getAddressLineTwo());
        if (student.getStudentAddress().getCity() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCity());
        if (student.getStudentAddress().getPinCode() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getPinCode());
        if (student.getStudentAddress().getState() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getState());
        if (student.getStudentAddress().getCountry() != null)
            studentAddress.append(", ").append(student.getStudentAddress().getCountry());

        return new RetrieveStudentNfcTapResultModel(
                school.getSchoolName(), null, null, fullName.toString(), student.getClassLevel(), student.getDivision(),
                student.getBloodGroup(), user.getMobileNumber(), user.getEmailId(),
                student.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), studentAddress.toString(),
                student.getEmergencyContactName(), student.getEmergencyContactNumber(),
                student.getEmergencyContactRelation(), student.getAlternateContactNumber()
        );
    }
}
