package com.techjagannath.digitalidentification.service.tapaxeadmin.impl;

import com.techjagannath.digitalidentification.entity.AddressMaster;
import com.techjagannath.digitalidentification.entity.RoleMaster;
import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.repository.AddressMasterRepository;
import com.techjagannath.digitalidentification.repository.RoleMasterRepository;
import com.techjagannath.digitalidentification.repository.SchoolMasterRepository;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import com.techjagannath.digitalidentification.service.tapaxeadmin.TapaxeAdminService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TapaxeAdminServiceImpl implements TapaxeAdminService {

    private final CommonMethods commonMethods;
    private final PasswordEncoder passwordEncoder;
    private final RoleMasterRepository roleMasterRepository;
    private static final String RESOURCE_NOT_FOUND = "Resource Not Found";
    private final AddressMasterRepository addressMasterRepository;
    private final UserMasterRepository userMasterRepository;
    private final SchoolMasterRepository schoolMasterRepository;

    public TapaxeAdminServiceImpl(CommonMethods commonMethods, PasswordEncoder passwordEncoder,
                                  RoleMasterRepository roleMasterRepository, AddressMasterRepository addressMasterRepository,
                                  UserMasterRepository userMasterRepository, SchoolMasterRepository schoolMasterRepository) {
        this.commonMethods = commonMethods;
        this.passwordEncoder = passwordEncoder;
        this.roleMasterRepository = roleMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.userMasterRepository = userMasterRepository;
        this.schoolMasterRepository = schoolMasterRepository;
    }

    @Override
    public AddSchoolAdminResultModel serviceEntryPointForAddSchoolAdmin(HttpServletRequest request, AddSchoolAdminRequestModel requestModel) {
        UserMaster tapaxeAdmin = this.commonMethods.extractUser(request);
        RoleMaster role = this.roleMasterRepository.findById(2).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        SchoolMaster school = this.schoolMasterRepository.findById(requestModel.getSchoolId()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        UserMaster schoolAdmin = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(),
                requestModel.getMiddleName(), requestModel.getMobileNo(),
                this.passwordEncoder.encode(requestModel.getPassword()),
                requestModel.getEmailId(), role, true,
                school, null, tapaxeAdmin, LocalDateTime.now());
        this.userMasterRepository.save(schoolAdmin);

        return new AddSchoolAdminResultModel(true);
    }

    @Override
    public AddSchoolResultModel serviceEntryPointForAddSchool(HttpServletRequest request, AddSchoolRequestModel requestModel) {
        UserMaster tapaxeAdmin = this.commonMethods.extractUser(request);

        AddressMaster address = new AddressMaster(null, requestModel.getAddressLineOne(),
                requestModel.getAddressLineTwo(), requestModel.getCity(), requestModel.getState(),
                requestModel.getPinCode(), requestModel.getCountry());
        AddressMaster savedAddress = this.addressMasterRepository.save(address);

        SchoolMaster school = new SchoolMaster(null, requestModel.getSchoolName(),
                savedAddress, requestModel.getSchoolContact(), requestModel.getSchoolEmailId(), tapaxeAdmin, LocalDateTime.now());
        this.schoolMasterRepository.save(school);
        return new AddSchoolResultModel(true);
    }
}
