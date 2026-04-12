package com.techjagannath.digital_identification.service.usermanagement.impl;

import com.techjagannath.digital_identification.entity.AddressMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.repository.AccountApprovalStatusRepository;
import com.techjagannath.digital_identification.repository.AddressMasterRepository;
import com.techjagannath.digital_identification.repository.RoleMasterRepository;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import com.techjagannath.digital_identification.service.usermanagement.UserManagementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final AddressMasterRepository addressMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AccountApprovalStatusRepository accountApprovalStatusRepository;
    private final UserMasterRepository userMasterRepository;

    public UserManagementServiceImpl(AddressMasterRepository addressMasterRepository, RoleMasterRepository roleMasterRepository,
                                     AccountApprovalStatusRepository accountApprovalStatusRepository, UserMasterRepository userMasterRepository) {
        this.addressMasterRepository = addressMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.accountApprovalStatusRepository = accountApprovalStatusRepository;
        this.userMasterRepository = userMasterRepository;
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
        AddressMaster savedAddress = this.addressMasterRepository.saveAndFlush(addressMaster);

        UserMaster user = new UserMaster();
        user.setFirstName(requestModel.getFirstName());
        user.setLastName(requestModel.getLastName());
        user.setEmailId(requestModel.getEmailId());
        user.setMobileNumber(requestModel.getPhoneNumber());
        user.setAlternateNumber(requestModel.getAlternateNumber());
        user.setAddress(savedAddress);
        user.setRole(this.roleMasterRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Default role not configured")));
        user.setIsActive(true);
        user.setApprovalStatus(this.accountApprovalStatusRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Default approval status not configured")));
        UserMaster savedUser = this.userMasterRepository.saveAndFlush(user);
        return new RegisterUserResultModel(savedUser.getId());
    }
}
