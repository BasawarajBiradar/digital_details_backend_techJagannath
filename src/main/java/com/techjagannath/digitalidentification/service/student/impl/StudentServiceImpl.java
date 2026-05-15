package com.techjagannath.digitalidentification.service.student.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapDetailsRequestModel;
import com.techjagannath.digitalidentification.models.student.nfccardtap.RetrieveStudentNfcTapResultModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidRequestModel;
import com.techjagannath.digitalidentification.models.student.registerstudentnfc.RegisterStudentUidResultModel;
import com.techjagannath.digitalidentification.models.student.todayentries.RetrieveStudentHomePageTodayEntriesResultModel;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.repository.*;
import com.techjagannath.digitalidentification.service.student.StudentService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final CommonMethods commonMethods;
    private final NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository;
    private final UserMasterRepository userMasterRepository;
    private final NfcUidMasterRepository nfcUidMasterRepository;
    private final SchoolMasterRepository schoolMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AddressMasterRepository addressMasterRepository;
    private final StudentDetailsMasterRepository studentDetailsMasterRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String UID_NOT_VALID = "Invalid UID";
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";

    public StudentServiceImpl(CommonMethods commonMethods, NfcCardTapsHistoryRepository nfcCardTapsHistoryRepository,
                              UserMasterRepository userMasterRepository, NfcUidMasterRepository nfcUidMasterRepository,
                              SchoolMasterRepository schoolMasterRepository, RoleMasterRepository roleMasterRepository,
                              AddressMasterRepository addressMasterRepository, StudentDetailsMasterRepository studentDetailsMasterRepository,
                              PasswordEncoder passwordEncoder) {
        this.commonMethods = commonMethods;
        this.nfcCardTapsHistoryRepository = nfcCardTapsHistoryRepository;
        this.userMasterRepository = userMasterRepository;
        this.nfcUidMasterRepository = nfcUidMasterRepository;
        this.schoolMasterRepository = schoolMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.studentDetailsMasterRepository = studentDetailsMasterRepository;
        this.passwordEncoder = passwordEncoder;
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
    public RetrieveStudentNfcTapResultModel serviceEntryPointForRetrieveStudentNfcTapDetails(String uid, RetrieveStudentNfcTapDetailsRequestModel requestModel) {
        UserMaster user = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));// this.userMasterRepository.findByUid(uid);
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

    @Override
    public VerifyNfcUidResultModel serviceEntryPointForVerifyStudentNfcUid(String uid) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        UserMaster user = result.get().getMappedUser();
        return new VerifyNfcUidResultModel( user != null ? user.getId() : null);
    }

    @Override
    public RegisterStudentUidResultModel serviceEntryPointForRegisterStudentNfcUid(String uid, RegisterStudentUidRequestModel requestModel) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        if (result.get().getMappedUser() != null)
            throw new DataIntegrityViolationException(UID_NOT_VALID);

        SchoolMaster schoolMaster = this.schoolMasterRepository.findById(requestModel.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        RoleMaster role = this.roleMasterRepository.findById(3).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        AddressMaster studentAddress = new AddressMaster(null, requestModel.getAddressLineOne(),
                requestModel.getAddressLineTwo(), requestModel.getCity(), requestModel.getState(), requestModel.getPinCode(), requestModel.getCountry());
        AddressMaster savedStudentAddress = this.addressMasterRepository.save(studentAddress);

        StudentDetailsMaster studentDetails = new StudentDetailsMaster(null, requestModel.getClassLevel(), requestModel.getDivision(),
                savedStudentAddress, requestModel.getBloodGroup(), requestModel.getBirthDate(), requestModel.getEmergencyContactName(),
                requestModel.getEmergencyContactRelation(), requestModel.getEmergencyContactNumber(), requestModel.getAlternateNumber(),
                null, LocalDateTime.now());
        StudentDetailsMaster savedStudentDetails = this.studentDetailsMasterRepository.save(studentDetails);

        UserMaster newUser = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(), requestModel.getMiddleName(),
                requestModel.getMobileNumber(), passwordEncoder.encode(requestModel.getPassword()),
                requestModel.getEmailId(), role, true, schoolMaster, savedStudentDetails, uid,
                null, LocalDateTime.now());
        UserMaster savedUser = this.userMasterRepository.save(newUser);

        NfcUidMaster nfcUidMaster = result.get();
        nfcUidMaster.setMappedUser(savedUser);
        this.nfcUidMasterRepository.save(nfcUidMaster);

        return new RegisterStudentUidResultModel(savedUser.getId());
    }
}
