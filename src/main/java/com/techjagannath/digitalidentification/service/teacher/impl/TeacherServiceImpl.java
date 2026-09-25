package com.techjagannath.digitalidentification.service.teacher.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.homepage.inforcard.RetrieveTeacherHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.repository.*;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import com.techjagannath.digitalidentification.utils.s3fileupload.S3Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final NfcUidMasterRepository nfcUidMasterRepository;
    private final SchoolMasterRepository schoolMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final TeacherDetailsMasterRepository teacherDetailsMasterRepository;
    private final AddressMasterRepository addressMasterRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMasterRepository userMasterRepository;
    private final SubjectsMasterRepository subjectsMasterRepository;
    private final CommonMethods commonMethods;
    private final HomeWorkDetailRecordsRepository homeworkDetailRecordsRepository;
    private final S3Utils s3Utils;
    private final StudentProfilePhotoRepoRepository studentProfilePhotoRepoRepository;
    private final SchoolLogoRepoRepository schoolLogoRepoRepository;

    private static final String UID_NOT_VALID = "Invalid UID";
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";

    public TeacherServiceImpl(NfcUidMasterRepository nfcUidMasterRepository, SchoolMasterRepository schoolMasterRepository,
                              RoleMasterRepository roleMasterRepository, TeacherDetailsMasterRepository teacherDetailsMasterRepository,
                              AddressMasterRepository addressMasterRepository, PasswordEncoder passwordEncoder, UserMasterRepository userMasterRepository,
                              SubjectsMasterRepository subjectsMasterRepository, CommonMethods commonMethods, HomeWorkDetailRecordsRepository homeworkDetailRecordsRepository,
                              S3Utils s3Utils, StudentProfilePhotoRepoRepository studentProfilePhotoRepoRepository, SchoolLogoRepoRepository schoolLogoRepoRepository) {
        this.nfcUidMasterRepository = nfcUidMasterRepository;
        this.schoolMasterRepository = schoolMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.teacherDetailsMasterRepository = teacherDetailsMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMasterRepository = userMasterRepository;
        this.subjectsMasterRepository = subjectsMasterRepository;
        this.commonMethods = commonMethods;
        this.homeworkDetailRecordsRepository = homeworkDetailRecordsRepository;
        this.s3Utils = s3Utils;
        this.studentProfilePhotoRepoRepository = studentProfilePhotoRepoRepository;
        this.schoolLogoRepoRepository = schoolLogoRepoRepository;
    }

    @Override
    public VerifyNfcUidResultModel serviceEntryPointForVerifyTeacherNfcUid(String uid) {
        Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
        if (result.isEmpty())
            throw new ResourceNotFoundException(UID_NOT_VALID);
        UserMaster user = result.get().getMappedUser();
        return new VerifyNfcUidResultModel( user != null ? user.getId() : null);
    }

    @Override
    public RegisterTeacherUidResultModel serviceEntryPointForRegisterTeacherNfcUid(String uid, RegisterTeacherUidRequestModel requestModel) {
         Optional<NfcUidMaster> result = this.nfcUidMasterRepository.findByUid(uid);
                if (result.isEmpty())
                    throw new ResourceNotFoundException(UID_NOT_VALID);
                if (result.get().getMappedUser() != null)
                    throw new DataIntegrityViolationException(UID_NOT_VALID);

                SchoolMaster schoolMaster = this.schoolMasterRepository.findById(requestModel.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
                RoleMaster role = this.roleMasterRepository.findById(4).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

                AddressMaster teacherAddress = new AddressMaster(null, requestModel.getAddressLineOne(),
                        requestModel.getAddressLineTwo(), requestModel.getCity(), requestModel.getState(), requestModel.getPinCode(), requestModel.getCountry());
                AddressMaster savedTeacherAddress = this.addressMasterRepository.save(teacherAddress);

                TeacherDetailsMaster teacherDetails = new TeacherDetailsMaster(null, requestModel.getClassTeacherOfClassLevel(), requestModel.getClassTeacherOfDivision(),
                        savedTeacherAddress, requestModel.getBloodGroup(), requestModel.getBirthDate(), requestModel.getEmergencyContactName(),
                        requestModel.getEmergencyContactRelation(), requestModel.getEmergencyContactNumber(), requestModel.getAlternateNumber(),
                        null, LocalDateTime.now());
                TeacherDetailsMaster savedTeacherDetails = this.teacherDetailsMasterRepository.save(teacherDetails);

                UserMaster newUser = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(), requestModel.getMiddleName(),
                        requestModel.getMobileNumber(), passwordEncoder.encode(requestModel.getPassword()),
                        requestModel.getEmailId(), role, true, schoolMaster, null, savedTeacherDetails, uid, false,
                        null, LocalDateTime.now());
                UserMaster savedUser = this.userMasterRepository.save(newUser);

                NfcUidMaster nfcUidMaster = result.get();
                nfcUidMaster.setMappedUser(savedUser);
                this.nfcUidMasterRepository.save(nfcUidMaster);

                return new RegisterTeacherUidResultModel(savedUser.getId());
    }

    @Override
    public TeacherAddHomeworkResultModel serviceEntryPointForAddHomework(HttpServletRequest request, TeacherAddHomeworkRequestModel requestModel) {
        UserMaster user = commonMethods.extractUser(request);
        Optional<SubjectsMaster> subjectsMaster = this.subjectsMasterRepository.findById(requestModel.getSubjectMasterId());
        HomeWorkDetailRecords recordDetail = new HomeWorkDetailRecords(null, subjectsMaster.orElse(new SubjectsMaster()),
                LocalDateTime.now(), requestModel.getParsedDeadlineDate(), requestModel.getClassLevel(), requestModel.getDivision(),
                user.getSchool(), user, requestModel.getHomeworkTitle(), requestModel.getDescription());
        HomeWorkDetailRecords savedRecords = this.homeworkDetailRecordsRepository.save(recordDetail);
        return new TeacherAddHomeworkResultModel(savedRecords.getTitleOrTopic());
    }

    @Override
    public RetrieveTeacherHomePageInfoCardDetailsResultModel serviceEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(" ").append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(" ").append(user.getLastName());

        SchoolMaster school = user.getSchool();
        TeacherDetailsMaster teacher = user.getTeacherDetails();

        StringBuilder teacherAddress = new StringBuilder();
        if (teacher.getAddress().getAddressLineOne() != null)
            teacherAddress.append(teacher.getAddress().getAddressLineOne());
        if (teacher.getAddress().getAddressLineTwo() != null)
            teacherAddress.append(", ").append(teacher.getAddress().getAddressLineTwo());
        if (teacher.getAddress().getCity() != null)
            teacherAddress.append(", ").append(teacher.getAddress().getCity());
        if (teacher.getAddress().getPinCode() != null)
            teacherAddress.append(", ").append(teacher.getAddress().getPinCode());
        if (teacher.getAddress().getState() != null)
            teacherAddress.append(", ").append(teacher.getAddress().getState());
        if (teacher.getAddress().getCountry() != null)
            teacherAddress.append(", ").append(teacher.getAddress().getCountry());

        StudentProfilePhotoRepo teacherProfile = this.studentProfilePhotoRepoRepository.findByMappedUserAndIsActive(user, true);
        SchoolLogoRepo schoolLogo = this.schoolLogoRepoRepository.findBySchoolAndIsActive(school, true);

        return new RetrieveTeacherHomePageInfoCardDetailsResultModel(
                school.getSchoolName(), schoolLogo != null ? this.s3Utils.generatePreSignedUrl(schoolLogo.getFileUrl()) : null,
                teacherProfile != null ? this.s3Utils.generatePreSignedUrl(teacherProfile.getFileUrl()) : null
                , fullName.toString(), teacher.getClassTeacherOfClassLevel(), teacher.getClassTeacherOfDivision(),
                teacher.getBloodGroup(), user.getMobileNumber(), user.getEmailId(),
                teacher.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), teacherAddress.toString(),
                teacher.getEmergencyContactName(), teacher.getEmergencyContactNumber(),
                teacher.getEmergencyContactRelation(), teacher.getAlternateContactNumber(), user.getUid()
        );
    }
}
