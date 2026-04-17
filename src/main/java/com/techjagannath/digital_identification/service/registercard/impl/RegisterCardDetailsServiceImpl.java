package com.techjagannath.digital_identification.service.registercard.impl;

import com.techjagannath.digital_identification.entity.AddressMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.ChildGuardianDetails;
import com.techjagannath.digital_identification.entity.profiles.ChildProfile;
import com.techjagannath.digital_identification.entity.profiles.SeniorCareTakerDetails;
import com.techjagannath.digital_identification.entity.profiles.SeniorProfile;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsGuardianDetails;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorCaretakerDetails;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.repository.AccountApprovalStatusRepository;
import com.techjagannath.digital_identification.repository.AddressMasterRepository;
import com.techjagannath.digital_identification.repository.RoleMasterRepository;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import com.techjagannath.digital_identification.repository.profiles.ChildGuardianDetailsRepository;
import com.techjagannath.digital_identification.repository.profiles.ChildProfileRepository;
import com.techjagannath.digital_identification.repository.profiles.SeniorCareTakerDetailsRepository;
import com.techjagannath.digital_identification.repository.profiles.SeniorProfileRepository;
import com.techjagannath.digital_identification.service.registercard.RegisterCardDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterCardDetailsServiceImpl implements RegisterCardDetailsService {

    private final UserMasterRepository userMasterRepository;
    private final ChildProfileRepository childProfileRepository;
    private final ChildGuardianDetailsRepository childGuardianDetailsRepository;
    private final AddressMasterRepository addressMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AccountApprovalStatusRepository accountApprovalStatusRepository;
    private final SeniorProfileRepository seniorProfileRepository;
    private final SeniorCareTakerDetailsRepository seniorCareTakerDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterCardDetailsServiceImpl(UserMasterRepository userMasterRepository, ChildProfileRepository childProfileRepository,
                                          ChildGuardianDetailsRepository childGuardianDetailsRepository, AddressMasterRepository addressMasterRepository,
                                          RoleMasterRepository roleMasterRepository, AccountApprovalStatusRepository accountApprovalStatusRepository,
                                          SeniorProfileRepository seniorProfileRepository, SeniorCareTakerDetailsRepository seniorCareTakerDetailsRepository) {
        this.userMasterRepository = userMasterRepository;
        this.childGuardianDetailsRepository = childGuardianDetailsRepository;
        this.childProfileRepository = childProfileRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.accountApprovalStatusRepository = accountApprovalStatusRepository;
        this.seniorProfileRepository = seniorProfileRepository;
        this.seniorCareTakerDetailsRepository = seniorCareTakerDetailsRepository;
    }

    @Override
    @Transactional
    public RegisterCardUserKidsDetailsResultModel serviceEntryPointForRegisterCardUserKidsDetails(RegisterCardUserKidsDetailsRequestModel requestModel) {
        /* user details */
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

        /* child details */
        ChildProfile childProfile = new ChildProfile();
        childProfile.setChildName(requestModel.getChildName());
        childProfile.setDateOfBirth(requestModel.getDateOfBirth());
        childProfile.setGender(requestModel.getGender());
        childProfile.setBloodGroup(requestModel.getBloodGroup());
        childProfile.setSchoolName(requestModel.getSchoolName());
        childProfile.setSchoolAddress(requestModel.getSchoolAddress());
        childProfile.setAllergies(requestModel.getAllergies());
        childProfile.setMedicalCondition(requestModel.getMedicalCondition());
        childProfile.setLinkedAccount(savedUser);
        ChildProfile savedChildProfile = this.childProfileRepository.save(childProfile);

        /* multiple guardian details */
        List<ChildGuardianDetails> guardianDetails = new ArrayList<>();
        for (RegisterCardUserKidsGuardianDetails model : requestModel.getGuardians()) {
            ChildGuardianDetails guardian = new ChildGuardianDetails();
            guardian.setGuardianName(model.getGuardian_name());
            guardian.setRelation(model.getRelationship());
            guardian.setPrimaryPhone(model.getPrimary_phone());
            guardian.setAlternatePhone(model.getAlternate_phone());
            guardian.setEmail(model.getEmail());
            guardian.setIsPrimary(false);
            guardian.setIdProofType(model.getId_proof_type());
            guardian.setIdProofNumber(model.getId_proof_number());
            guardian.setChildProfile(savedChildProfile);
        }
        this.childGuardianDetailsRepository.saveAll(guardianDetails);
        return new RegisterCardUserKidsDetailsResultModel(childProfile.getChildName());
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel serviceEntryPointForRegisterCardUserSeniorDetails(RegisterCardUserSeniorDetailsRequestModel requestModel) {
        /* user details */
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

        /* senior details */
        SeniorProfile seniorProfile = new SeniorProfile();
        seniorProfile.setFullName(requestModel.getFullName());
        seniorProfile.setDateOfBirth(requestModel.getDateOfBirth());
        seniorProfile.setGender(requestModel.getGender());
        seniorProfile.setBloodGroup(requestModel.getBloodGroup());
        seniorProfile.setMedicalConditions(requestModel.getMedicalCondition());
        seniorProfile.setCurrentMedications(requestModel.getMedications());
        seniorProfile.setDoctorName(requestModel.getDoctorName());
        seniorProfile.setDoctorContact(requestModel.getDoctorContact());
        seniorProfile.setHospitalPreference(requestModel.getHospitalPreference());
        seniorProfile.setInsuranceProvider(requestModel.getInsuranceProvider());
        seniorProfile.setInsuranceNumber(requestModel.getInsuranceNumber());
        seniorProfile.setLinkedAccount(savedUser);
        SeniorProfile savedSeniorProfile = this.seniorProfileRepository.save(seniorProfile);

        /* multiple care taker details */
        List<SeniorCareTakerDetails> careTakersDetails = new ArrayList<>();
        for(RegisterCardUserSeniorCaretakerDetails model : requestModel.getCaretakers()) {
            SeniorCareTakerDetails careTakerDetails = new SeniorCareTakerDetails();
            careTakerDetails.setCareTakerName(model.getCareTakerName());
            careTakerDetails.setRelation(model.getRelationship());
            careTakerDetails.setPrimaryPhone(model.getPhone());
            careTakerDetails.setAlternatePhone(model.getAlternateNumber());
            careTakerDetails.setEmail(null);
            careTakerDetails.setIsPrimary(false);
            careTakerDetails.setSeniorProfile(savedSeniorProfile);
        }
        this.seniorCareTakerDetailsRepository.saveAll(careTakersDetails);
        return new RegisterCardUserSeniorDetailsResultModel(savedSeniorProfile.getFullName());
    }
}
