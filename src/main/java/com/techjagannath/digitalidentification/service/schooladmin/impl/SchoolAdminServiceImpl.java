package com.techjagannath.digitalidentification.service.schooladmin.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.addstudent.AddStudentBySchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListRequestModel;
import com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist.RetrieveStudentsListResultModel;
import com.techjagannath.digitalidentification.repository.AddressMasterRepository;
import com.techjagannath.digitalidentification.repository.RoleMasterRepository;
import com.techjagannath.digitalidentification.repository.StudentDetailsMasterRepository;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import com.techjagannath.digitalidentification.service.schooladmin.SchoolAdminService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class SchoolAdminServiceImpl implements SchoolAdminService {

    private final CommonMethods commonMethods;
    private final UserMasterRepository userMasterRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleMasterRepository roleMasterRepository;
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";
    private final StudentDetailsMasterRepository studentDetailsMasterRepository;
    private final AddressMasterRepository addressMasterRepository;

    public SchoolAdminServiceImpl(CommonMethods commonMethods, UserMasterRepository userMasterRepository,
                                  PasswordEncoder passwordEncoder, RoleMasterRepository roleMasterRepository,
                                  StudentDetailsMasterRepository studentDetailsMasterRepository, AddressMasterRepository addressMasterRepository) {
        this.commonMethods = commonMethods;
        this.userMasterRepository = userMasterRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleMasterRepository = roleMasterRepository;
        this.studentDetailsMasterRepository = studentDetailsMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
    }

    @Override
    public AddStudentBySchoolAdminResultModel serviceEntryPointForAddStudentBySchoolAdmin(HttpServletRequest request, AddStudentBySchoolAdminRequestModel requestModel) {
        UserMaster adminUser = this.commonMethods.extractUser(request);
        SchoolMaster schoolMaster = adminUser.getSchool();
        RoleMaster role = this.roleMasterRepository.findById(3).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        AddressMaster studentAddress = new AddressMaster(null, requestModel.getAddressLineOne(),
                requestModel.getAddressLineTwo(), requestModel.getCity(), requestModel.getState(), requestModel.getPinCode(), requestModel.getCountry());
        AddressMaster savedStudentAddress = this.addressMasterRepository.save(studentAddress);

        StudentDetailsMaster studentDetails = new StudentDetailsMaster(null, requestModel.getClassLevel(), requestModel.getDivision(),
                savedStudentAddress, requestModel.getBloodGroup(), requestModel.getBirthDate(), requestModel.getEmergencyContactName(),
                requestModel.getEmergencyContactRelation(), requestModel.getEmergencyContactNumber(), requestModel.getAlternateNumber(),
                adminUser, LocalDateTime.now());
        StudentDetailsMaster savedStudentDetails = this.studentDetailsMasterRepository.save(studentDetails);

        UserMaster newUser = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(), requestModel.getMiddleName(),
                requestModel.getMobileNumber(), passwordEncoder.encode(requestModel.getFirstName()+"@"+requestModel.getBirthDate().toString()),
                requestModel.getEmailId(), role, true, schoolMaster, savedStudentDetails, null,
                adminUser, LocalDateTime.now());

        this.userMasterRepository.save(newUser);
        return new AddStudentBySchoolAdminResultModel(true);
    }

    @Override
    public List<RetrieveStudentsListResultModel> serviceEntryPointForRetrieveStudentsList(HttpServletRequest request, RetrieveStudentsListRequestModel requestModel) {
        UserMaster adminUser = this.commonMethods.extractUser(request);
        List<Object[]> resultList = this.userMasterRepository.retrieveStudentsListBySchool(
                adminUser.getSchool().getId(), requestModel.getSize());
        List<RetrieveStudentsListResultModel> resultModels = new LinkedList<>();
        for (Object[] res : resultList) {
            Long id = Long.parseLong(res[0].toString());
            String firstName = res[1].toString();
            String middleName = res[2] == null ? null : res[2].toString();
            String lastName = res[3].toString();
            String classLevel = res[4].toString();
            String division = res[5] == null ? null : res[5].toString();
            String registrationDate = res[6].toString().split(" ")[0];

            StringBuilder fullName = new StringBuilder(firstName);
            if (middleName != null)
                fullName.append(" ").append(middleName);
            fullName.append(" ").append(lastName);

            resultModels.add(new RetrieveStudentsListResultModel(id, fullName.toString(),
                    classLevel, division, registrationDate));
        }
        return resultModels;
    }
}
