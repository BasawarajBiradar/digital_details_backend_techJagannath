package com.techjagannath.digitalidentification.service.tapaxeadmin.impl;

import com.techjagannath.digitalidentification.entity.*;
import com.techjagannath.digitalidentification.exception.ResourceNotFoundException;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addnfcuid.AddNfcUidRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addnfcuid.AddNfcUidResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddLTapaxeAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddTapaxeAdminRequestModel;
import com.techjagannath.digitalidentification.repository.*;
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
    private final NfcUidMasterRepository nfcUidMasterRepository;

    public TapaxeAdminServiceImpl(CommonMethods commonMethods, PasswordEncoder passwordEncoder, NfcUidMasterRepository nfcUidMasterRepository,
                                  RoleMasterRepository roleMasterRepository, AddressMasterRepository addressMasterRepository,
                                  UserMasterRepository userMasterRepository, SchoolMasterRepository schoolMasterRepository) {
        this.commonMethods = commonMethods;
        this.passwordEncoder = passwordEncoder;
        this.roleMasterRepository = roleMasterRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.userMasterRepository = userMasterRepository;
        this.schoolMasterRepository = schoolMasterRepository;
        this.nfcUidMasterRepository = nfcUidMasterRepository;
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
                school, null, null, false, tapaxeAdmin, LocalDateTime.now());
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

    @Override
    public AddLTapaxeAdminResultModel serviceEntryPointForAddTapaxeAdmin(AddTapaxeAdminRequestModel requestModel) {
        RoleMaster role = this.roleMasterRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        UserMaster adminUser = new UserMaster(null, requestModel.getFirstName(), requestModel.getLastName(),
                requestModel.getMiddleName(), requestModel.getMobileNo(),
                this.passwordEncoder.encode(requestModel.getPassword()),
                requestModel.getEmailId(), role, true,
                null, null, null, false,null, LocalDateTime.now());
        UserMaster savedUser = this.userMasterRepository.save(adminUser);

        return new AddLTapaxeAdminResultModel(savedUser.getId());
    }

    @Override
    public AddNfcUidResultModel serviceEntryPointForAddNfcUid(HttpServletRequest request, AddNfcUidRequestModel requestModel) {
        UserMaster adminUser = this.commonMethods.extractUser(request);
        NfcUidMaster nfcUidMaster = new NfcUidMaster(
                null, requestModel.getUid(), null, adminUser, LocalDateTime.now());
        NfcUidMaster savedNfcUidMaster = this.nfcUidMasterRepository.save(nfcUidMaster);
        return new AddNfcUidResultModel("https://tapaxe.techjagannath.com"+"/student/"+savedNfcUidMaster.getUid());
    }
}
