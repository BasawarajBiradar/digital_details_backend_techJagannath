package com.techjagannath.digital_identification.service.usermanagement.impl;

import com.techjagannath.digital_identification.entity.AddressMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.ChildProfile;
import com.techjagannath.digital_identification.exception.ResourceNotFoundException;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsResultModel;
import com.techjagannath.digital_identification.repository.AccountApprovalStatusRepository;
import com.techjagannath.digital_identification.repository.AddressMasterRepository;
import com.techjagannath.digital_identification.repository.RoleMasterRepository;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import com.techjagannath.digital_identification.repository.profiles.ChildProfileRepository;
import com.techjagannath.digital_identification.service.usermanagement.UserManagementService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final AddressMasterRepository addressMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AccountApprovalStatusRepository accountApprovalStatusRepository;
    private final UserMasterRepository userMasterRepository;
    private final ChildProfileRepository childProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(AddressMasterRepository addressMasterRepository, RoleMasterRepository roleMasterRepository,
                                     AccountApprovalStatusRepository accountApprovalStatusRepository, UserMasterRepository userMasterRepository,
                                     ChildProfileRepository childProfileRepository) {
        this.addressMasterRepository = addressMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.accountApprovalStatusRepository = accountApprovalStatusRepository;
        this.userMasterRepository = userMasterRepository;
        this.childProfileRepository = childProfileRepository;
    }

    @Override
    @Transactional
    public RegisterUserResultModel serviceEntryPointForRegisterUser(RegisterUserRequestModel requestModel) {
        AddressMaster addressMaster = new AddressMaster();
        addressMaster.setAddressLineOne(requestModel.getAddressLineOne());
        addressMaster.setAddressLineTwo(requestModel.getAddressLineTwo());
        addressMaster.setCity(requestModel.getCity());
        addressMaster.setCountry(requestModel.getCountry());
        addressMaster.setState(requestModel.getState());
        addressMaster.setPinCode(requestModel.getPinCode());
        AddressMaster savedAddress = this.addressMasterRepository.save(addressMaster);

        UserMaster user = new UserMaster();
        if (userMasterRepository.findByEmailId(requestModel.getEmailId()) != null)
            throw new DataIntegrityViolationException("Email already exists");
        user.setFirstName(requestModel.getFirstName());
        user.setLastName(requestModel.getLastName());
        user.setEmailId(requestModel.getEmailId());
        user.setMobileNumber(requestModel.getPhoneNumber());
        user.setAlternateNumber(requestModel.getAlternateNumber());
        user.setAddress(savedAddress);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(
                passwordEncoder.encode(requestModel.getPassword())
        );
        user.setRole(this.roleMasterRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Default role not configured")));
        user.setIsActive(true);
        user.setApprovalStatus(this.accountApprovalStatusRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Default approval status not configured")));
        UserMaster savedUser = this.userMasterRepository.save(user);
        return new RegisterUserResultModel(savedUser.getId());
    }

    @Override
    public SaveUserChildProfileDetailsResultModel serviceEntryPointForSaveUserChildProfileDetails(Long userId, SaveUserChildProfileDetailsRequestModel requestModel) {
        ChildProfile childProfile = new ChildProfile();
        childProfile.setChildName(requestModel.getChildName());
        childProfile.setDateOfBirth(requestModel.getDateOfBirth());
        childProfile.setGender(requestModel.getGender());
        childProfile.setBloodGroup(requestModel.getBloodGroup());
        childProfile.setSchoolName(requestModel.getSchoolName());
        childProfile.setSchoolAddress(requestModel.getSchoolAddress());
        childProfile.setAllergies(requestModel.getAllergies());
        childProfile.setMedicalCondition(requestModel.getMedicalConditions());
        childProfile.setLinkedAccount(this.userMasterRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found")));
        ChildProfile savedChild = this.childProfileRepository.save(childProfile);
        return new SaveUserChildProfileDetailsResultModel(savedChild.getId());
    }
}
