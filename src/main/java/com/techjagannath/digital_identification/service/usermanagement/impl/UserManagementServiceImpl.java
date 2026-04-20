package com.techjagannath.digital_identification.service.usermanagement.impl;

import com.techjagannath.digital_identification.config.JwtUtil;
import com.techjagannath.digital_identification.entity.AddressMaster;
import com.techjagannath.digital_identification.entity.ProfileTypesMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.*;
import com.techjagannath.digital_identification.exception.ResourceNotFoundException;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.RegisterUserResultModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.RegisterCardUserKidsGuardianDetails;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals.SaveUserChildProfileDetailsResultModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorCaretakerDetails;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.usermanagement.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.repository.*;
import com.techjagannath.digital_identification.repository.profiles.*;
import com.techjagannath.digital_identification.service.usermanagement.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final AddressMasterRepository addressMasterRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final AccountApprovalStatusRepository accountApprovalStatusRepository;
    private final UserMasterRepository userMasterRepository;
    private final ChildProfileRepository childProfileRepository;
    private final UserProfileNfcMappingRepository userProfileNfcMappingRepository;
    private final ProfileTypesMasterRepository profileTypesMasterRepository;
    private final JwtUtil jwtUtil;
    private final ChildGuardianDetailsRepository childGuardianDetailsRepository;
    private final SeniorProfileRepository seniorProfileRepository;
    private final SeniorCareTakerDetailsRepository seniorCareTakerDetailsRepository;
    private final BusinessProfileRepository businessProfileRepository;
    private final VehicleProfileRepository vehicleProfileRepository;
    private final PetProfileRepository petProfileRepository;
    private final SocialProfileRepository socialProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(AddressMasterRepository addressMasterRepository, RoleMasterRepository roleMasterRepository,
                                     AccountApprovalStatusRepository accountApprovalStatusRepository, UserMasterRepository userMasterRepository,
                                     ChildProfileRepository childProfileRepository, UserProfileNfcMappingRepository userProfileNfcMappingRepository,
                                     ProfileTypesMasterRepository profileTypesMasterRepository, JwtUtil jwtUtil,
                                     ChildGuardianDetailsRepository childGuardianDetailsRepository,
                                     SeniorProfileRepository seniorProfileRepository, SeniorCareTakerDetailsRepository seniorCareTakerDetailsRepository,
                                     BusinessProfileRepository businessProfileRepository, VehicleProfileRepository vehicleProfileRepository,
                                     PetProfileRepository petProfileRepository, SocialProfileRepository socialProfileRepository) {
        this.addressMasterRepository = addressMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.accountApprovalStatusRepository = accountApprovalStatusRepository;
        this.userMasterRepository = userMasterRepository;
        this.childProfileRepository = childProfileRepository;
        this.userProfileNfcMappingRepository = userProfileNfcMappingRepository;
        this.profileTypesMasterRepository = profileTypesMasterRepository;
        this.jwtUtil = jwtUtil;
        this.childGuardianDetailsRepository = childGuardianDetailsRepository;
        this.seniorProfileRepository = seniorProfileRepository;
        this.seniorCareTakerDetailsRepository = seniorCareTakerDetailsRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.vehicleProfileRepository = vehicleProfileRepository;
        this.petProfileRepository = petProfileRepository;
        this.socialProfileRepository = socialProfileRepository;
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

    private String generateUid() {
        // Step 1: today's date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "UID" + date;
        // Step 2: get latest UID
        String latestUid = this.userProfileNfcMappingRepository.findLatestUidByPrefix(prefix);
        int nextNumber = 1;
        if (latestUid != null) {
            // Step 3: extract last 4 digits
            String numberPart = latestUid.substring(latestUid.length() - 4);
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        // Step 4: format with leading zeros
        String formattedNumber = String.format("%04d", nextNumber);
        return prefix + formattedNumber;
    }

    private String extractUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return jwtUtil.extractUsername(token);
    }

    @Override
    public SaveUserChildProfileDetailsResultModel serviceEntryPointForSaveUserChildProfileDetails(HttpServletRequest request, SaveUserChildProfileDetailsRequestModel requestModel) {
        String email = this.extractUser(request);
        UserMaster user = this.userMasterRepository.findByEmailId(email);
        ChildProfile childProfile = new ChildProfile();
        childProfile.setChildName(requestModel.getChildName());
        childProfile.setDateOfBirth(requestModel.getDateOfBirth());
        childProfile.setGender(requestModel.getGender());
        childProfile.setBloodGroup(requestModel.getBloodGroup());
        childProfile.setSchoolName(requestModel.getSchoolName());
        childProfile.setSchoolAddress(requestModel.getSchoolAddress());
        childProfile.setAllergies(requestModel.getAllergies());
        childProfile.setMedicalCondition(requestModel.getMedicalConditions());
        childProfile.setLinkedAccount(user);
        ChildProfile savedChild = this.childProfileRepository.save(childProfile);

        String uid = generateUid();
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping uidMapping = new UserProfileNfcMapping(null, user, profileType, uid);
        this.userProfileNfcMappingRepository.save(uidMapping);

        List<ChildGuardianDetails> guardianDetails = new ArrayList<>();
        for (RegisterCardUserKidsGuardianDetails model : requestModel.getGuardians()) {
            ChildGuardianDetails guardian = new ChildGuardianDetails();
            guardian.setGuardianName(model.getGuardianName());
            guardian.setRelation(model.getRelationship());
            guardian.setPrimaryPhone(model.getPrimaryPhone());
            guardian.setAlternatePhone(model.getAlternatePhone());
            guardian.setEmail(model.getEmail());
            guardian.setIsPrimary(false);
            guardian.setIdProofType(model.getIdProofType());
            guardian.setIdProofNumber(model.getIdProofNumber());
            guardian.setChildProfile(savedChild);

            guardianDetails.add(guardian);
        }
        this.childGuardianDetailsRepository.saveAll(guardianDetails);
        return new SaveUserChildProfileDetailsResultModel(savedChild.getId());
    }

    @Override
    public RegisterCardUserSeniorDetailsResultModel serviceEntryPointForSaveSeniorProfileDetails(HttpServletRequest request, RegisterCardUserSeniorDetailsRequestModel requestModel) {
        String email = this.extractUser(request);
        UserMaster user = this.userMasterRepository.findByEmailId(email);
        SeniorProfile seniorProfile = new SeniorProfile(null, requestModel.getFullName(),
                requestModel.getDateOfBirth(), requestModel.getGender(), requestModel.getBloodGroup(), requestModel.getMedicalCondition(),
                requestModel.getMedicalCondition(), requestModel.getDoctorName(), requestModel.getDoctorContact(), requestModel.getHospitalPreference(),
                requestModel.getInsuranceProvider(), requestModel.getInsuranceNumber(), user);
        SeniorProfile savedSeniorProfile = this.seniorProfileRepository.save(seniorProfile);

        String uid = generateUid();
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(2).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping uidMapping = new UserProfileNfcMapping(null, user, profileType, uid);
        this.userProfileNfcMappingRepository.save(uidMapping);

        List<SeniorCareTakerDetails> careTakerDetails = new ArrayList<>();
        for (RegisterCardUserSeniorCaretakerDetails careTaker : requestModel.getCaretakers())
            careTakerDetails.add(new SeniorCareTakerDetails(null, careTaker.getCareTakerName(), careTaker.getRelationship(),
                    careTaker.getPhone(), careTaker.getAlternateNumber(), null, careTaker.getIsPrimary(), savedSeniorProfile));
        this.seniorCareTakerDetailsRepository.saveAll(careTakerDetails);

        return new RegisterCardUserSeniorDetailsResultModel(savedSeniorProfile.getFullName());
    }
}
