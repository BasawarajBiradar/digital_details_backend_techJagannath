package com.techjagannath.digitalidentification.service.teacher.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.student.verifyuid.VerifyNfcUidResultModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkRequestModel;
import com.techjagannath.digitalidentification.models.teacher.addhomework.TeacherAddHomeworkResultModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidRequestModel;
import com.techjagannath.digitalidentification.models.teacher.register.RegisterTeacherUidResultModel;
import com.techjagannath.digitalidentification.repository.*;
import com.techjagannath.digitalidentification.service.teacher.TeacherService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private static final String UID_NOT_VALID = "Invalid UID";
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";

    public TeacherServiceImpl(NfcUidMasterRepository nfcUidMasterRepository, SchoolMasterRepository schoolMasterRepository,
                              RoleMasterRepository roleMasterRepository, TeacherDetailsMasterRepository teacherDetailsMasterRepository,
                              AddressMasterRepository addressMasterRepository, PasswordEncoder passwordEncoder, UserMasterRepository userMasterRepository,
                              SubjectsMasterRepository subjectsMasterRepository, CommonMethods commonMethods, HomeWorkDetailRecordsRepository homeworkDetailRecordsRepository) {
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
}
