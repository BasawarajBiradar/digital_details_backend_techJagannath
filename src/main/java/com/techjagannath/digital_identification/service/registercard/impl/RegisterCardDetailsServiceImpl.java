package com.techjagannath.digital_identification.service.registercard.impl;

import com.techjagannath.digital_identification.entity.AddressMaster;
import com.techjagannath.digital_identification.entity.ProfileTypesMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.*;
import com.techjagannath.digital_identification.exception.ResourceNotFoundException;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsGuardianDetails;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles.*;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorCaretakerDetails;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsResultModel;
import com.techjagannath.digital_identification.repository.*;
import com.techjagannath.digital_identification.repository.profiles.*;
import com.techjagannath.digital_identification.service.registercard.RegisterCardDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final BusinessProfileRepository businessProfileRepository;
    private final VehicleProfileRepository vehicleProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final PetProfileRepository petProfileRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final UserProfileNfcMappingRepository userProfileNfcMappingRepository;
    private final ProfileTypesMasterRepository profileTypesMasterRepository;

    public RegisterCardDetailsServiceImpl(UserMasterRepository userMasterRepository, ChildProfileRepository childProfileRepository, PasswordEncoder passwordEncoder,
                                          ChildGuardianDetailsRepository childGuardianDetailsRepository, AddressMasterRepository addressMasterRepository,
                                          RoleMasterRepository roleMasterRepository, AccountApprovalStatusRepository accountApprovalStatusRepository,
                                          SeniorProfileRepository seniorProfileRepository, SeniorCareTakerDetailsRepository seniorCareTakerDetailsRepository,
                                          BusinessProfileRepository businessProfileRepository, VehicleProfileRepository vehicleProfileRepository,
                                          PetProfileRepository petProfileRepository, SocialProfileRepository socialProfileRepository,
                                          UserProfileNfcMappingRepository userProfileNfcMappingRepository, ProfileTypesMasterRepository profileTypesMasterRepository) {
        this.userMasterRepository = userMasterRepository;
        this.childGuardianDetailsRepository = childGuardianDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.childProfileRepository = childProfileRepository;
        this.addressMasterRepository = addressMasterRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.accountApprovalStatusRepository = accountApprovalStatusRepository;
        this.seniorProfileRepository = seniorProfileRepository;
        this.seniorCareTakerDetailsRepository = seniorCareTakerDetailsRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.vehicleProfileRepository = vehicleProfileRepository;
        this.petProfileRepository = petProfileRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.userProfileNfcMappingRepository = userProfileNfcMappingRepository;
        this.profileTypesMasterRepository = profileTypesMasterRepository;
    }

    @Override
    @Transactional
    public RegisterCardUserKidsDetailsResultModel serviceEntryPointForRegisterCardUserKidsDetails(String uid, RegisterCardUserKidsDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

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
    public RegisterCardUserSeniorDetailsResultModel serviceEntryPointForRegisterCardUserSeniorDetails(String uid, RegisterCardUserSeniorDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(2).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

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

    @Override
    public RegisterCardUserBusinessDetailsResultModel serviceEntryPointForRegisterCardUserBusinessDetails(String uid, RegisterCardUserBusinessDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(3).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

        /* business details */
        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setBusinessName(requestModel.getBusinessName());
        businessProfile.setBusinessDescription(null);
        businessProfile.setBusinessType(requestModel.getBusinessType());
        businessProfile.setRegistrationNumber(requestModel.getRegistrationNumber());
        businessProfile.setGstNumber(requestModel.getGstNumber());
        businessProfile.setBusinessEmail(requestModel.getBusinessEmail());
        businessProfile.setBusinessPhone(requestModel.getBusinessPhone());
        businessProfile.setWebsiteUrl(requestModel.getWebsiteUrl());
        businessProfile.setBusinessAddress(requestModel.getBusinessAddress());
        businessProfile.setLinkedAccount(savedUser);
        BusinessProfile savedBusinessProfile = this.businessProfileRepository.save(businessProfile);

        return new RegisterCardUserBusinessDetailsResultModel(savedBusinessProfile.getBusinessName());
    }

    @Override
    public RegisterCardUserVehicleDetailsResultModel serviceEntryPointForRegisterCardUserVehicleDetails(String uid, RegisterCardUserVehicleDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(4).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

        /* vehicle details */
        VehicleProfile vehicleProfile = new VehicleProfile();
        vehicleProfile.setVehicleNumber(requestModel.getVehicleNumber());
        vehicleProfile.setVehicleType(requestModel.getVehicleType());
        vehicleProfile.setBrand(requestModel.getBrand());
        vehicleProfile.setModel(requestModel.getModel());
        vehicleProfile.setColour(requestModel.getColor());
        vehicleProfile.setYearOfManufacturing(LocalDate.parse(requestModel.getYearOfManufacture()));
        vehicleProfile.setOwnerName(requestModel.getOwnerName());
        vehicleProfile.setOwnerContact(requestModel.getOwnerContact());
        vehicleProfile.setAlternateContact(requestModel.getAlternateContact());
        vehicleProfile.setRcNumber(requestModel.getRcNumber());
        vehicleProfile.setInsuranceNumber(requestModel.getInsuranceNumber());
        vehicleProfile.setInsuranceExpiry(LocalDate.parse(requestModel.getInsuranceExpiry()));
        vehicleProfile.setChassisNumber(requestModel.getChassisNumber());
        vehicleProfile.setLinkedAccount(savedUser);
        VehicleProfile savedVehicleProfile = this.vehicleProfileRepository.save(vehicleProfile);

        return new RegisterCardUserVehicleDetailsResultModel(savedVehicleProfile.getVehicleNumber());
    }

    @Override
    public RegisterCardUserPetsDetailsResultModel serviceEntryPointForRegisterCardUserPetDetails(String uid, RegisterCardUserPetsDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(5).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

        /* pet details */
        PetProfile petProfile = new PetProfile();
        petProfile.setPetName(requestModel.getPetName());
        petProfile.setSpecies(requestModel.getSpecies());
        petProfile.setBreed(requestModel.getBreed());
        petProfile.setGender(requestModel.getGender());
        petProfile.setAge(requestModel.getAge());
        petProfile.setColour(requestModel.getColor());
        petProfile.setMicroChipId(requestModel.getMicrochipId());
        petProfile.setVaccinationStatus(requestModel.getVaccinationStatus());
        petProfile.setVetName(requestModel.getVetName());
        petProfile.setVetContact(requestModel.getVetContact());
        petProfile.setMedicalNotes(requestModel.getMedialNotes());
        petProfile.setOwnerName(requestModel.getOwnerName());
        petProfile.setOwnerContact(requestModel.getOwnerContact());
        petProfile.setAlternateContact(requestModel.getAlternateContact());
        petProfile.setLinkedAccount(savedUser);
        PetProfile savedPetProfile = this.petProfileRepository.save(petProfile);

        return new RegisterCardUserPetsDetailsResultModel(savedPetProfile.getPetName());
    }

    @Override
    public RegisterCardUserSocialDetailsResultModel serviceEntryPointForRegisterCardUserSocialDetails(String uid, RegisterCardUserSocialDetailsRequestModel requestModel) {
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

        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(6).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid);
        this.userProfileNfcMappingRepository.save(mapping);

        /* social profile */
        SocialProfile socialProfile = new SocialProfile();
        socialProfile.setFullName(requestModel.getFullName());
        socialProfile.setNickName(requestModel.getNickname());
        socialProfile.setInstagram(requestModel.getInstagramHandle());
        socialProfile.setFacebook(requestModel.getFacebookProfile());
        socialProfile.setLinkedIn(requestModel.getLinkedinProfile());
        socialProfile.setTwitter(requestModel.getTwitterHandle());
        socialProfile.setEmergencyContactName(requestModel.getEmergencyContactName());
        socialProfile.setEmergencyContactNumber(requestModel.getEmergencyContactNumber());
        socialProfile.setMessageToFinder(requestModel.getMessageToFinder());
        socialProfile.setLinkedAccount(savedUser);
        SocialProfile savedSocialProfile = this.socialProfileRepository.save(socialProfile);
        return new RegisterCardUserSocialDetailsResultModel(savedSocialProfile.getFullName());
    }

    @Override
    public RetrieveUserCardDetailsResultModel serviceEntryPointForRetrieveUserDetails(RetrieveUserCardDetailsRequestModel requestModel) {
        UserProfileNfcMapping mapping = this.userProfileNfcMappingRepository.findByUid(requestModel.getUid());
        if (mapping == null)
            return new RetrieveUserCardDetailsResultModel();
        RetrieveUserCardDetailsResultModel resultModel = new RetrieveUserCardDetailsResultModel();
        resultModel.setAccountType(mapping.getProfileType().getProfileType());

        if (mapping.getProfileType().getId() == 1) {
            ChildProfile childProfile = this.childProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            List<ChildGuardianDetails> guardianDetailsResultList = this.childGuardianDetailsRepository.findAllByChildProfile(childProfile);
            List<RetrieveUserCardGuardianDetailsResultModel> guardianList = new ArrayList<>();
            for (ChildGuardianDetails model : guardianDetailsResultList)
                guardianList.add(new RetrieveUserCardGuardianDetailsResultModel(
                        model.getEmail(), model.getAlternatePhone(), model.getGuardianName(), model.getIdProofNumber(), model.getIdProofType(),
                        model.getIsPrimary(), model.getPrimaryPhone(), model.getRelation()));
            resultModel.setChildProfile(new ChildProfileResultModel(childProfile.getChildName(), childProfile.getDateOfBirth(),
                    childProfile.getGender(), childProfile.getBloodGroup(), childProfile.getSchoolName(), childProfile.getSchoolAddress(), childProfile.getAllergies(), childProfile.getMedicalCondition(),
                    guardianList));
        }

        else if (mapping.getProfileType().getId() == 2) {
            SeniorProfile seniorProfile = this.seniorProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            List<SeniorCareTakerDetails> careTakerResultList = this.seniorCareTakerDetailsRepository.findAllBySeniorProfile(seniorProfile);
            List<RetrieveUserCardCareTakerDetailsResultModel> caretakerList = new ArrayList<>();
            for (SeniorCareTakerDetails model : careTakerResultList)
                caretakerList.add(new RetrieveUserCardCareTakerDetailsResultModel(model.getCareTakerName(), model.getRelation(),
                        model.getPrimaryPhone(), model.getAlternatePhone(), model.getIsPrimary()));
            resultModel.setSeniorProfile(new SeniorProfileResultModel(seniorProfile.getFullName(), seniorProfile.getDateOfBirth(),
                    seniorProfile.getGender(), seniorProfile.getBloodGroup(), seniorProfile.getMedicalConditions(), seniorProfile.getCurrentMedications(),
                    seniorProfile.getDoctorName(), seniorProfile.getDoctorContact(), seniorProfile.getHospitalPreference(), seniorProfile.getInsuranceProvider(),
                    seniorProfile.getInsuranceNumber(), caretakerList));

        }

        else if (mapping.getProfileType().getId() == 3) {
            BusinessProfile businessProfile = this.businessProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            resultModel.setBusinessProfile(new BusinessProfileResultModel(businessProfile.getBusinessName(), businessProfile.getBusinessType(),
                    businessProfile.getRegistrationNumber(), businessProfile.getGstNumber(), businessProfile.getBusinessEmail(),
                    businessProfile.getBusinessPhone(), businessProfile.getBusinessAddress(), businessProfile.getWebsiteUrl(),
                    null, null, null
                    ));
        }

        else if (mapping.getProfileType().getId() == 4) {
            VehicleProfile vehicleProfile = this.vehicleProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            resultModel.setVehicleProfile(new VehicleProfileResultModel(vehicleProfile.getVehicleNumber(), vehicleProfile.getVehicleType(),
                    vehicleProfile.getBrand(), vehicleProfile.getModel(), vehicleProfile.getColour(), vehicleProfile.getYearOfManufacturing().toString(),
                    vehicleProfile.getOwnerName(), vehicleProfile.getOwnerContact(), vehicleProfile.getAlternateContact(), vehicleProfile.getRcNumber(),
                    vehicleProfile.getInsuranceNumber(), vehicleProfile.getInsuranceExpiry().toString(), vehicleProfile.getChassisNumber()));
        }

        else if (mapping.getProfileType().getId() == 5) {
            PetProfile petProfile = this.petProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            resultModel.setPetProfile(new PetProfileResultModel(petProfile.getPetName(), petProfile.getSpecies(), petProfile.getBreed(),
                    petProfile.getGender(), petProfile.getAge(), petProfile.getColour(), petProfile.getMicroChipId(), petProfile.getVaccinationStatus(),
                    petProfile.getVetName(), petProfile.getVetContact(), petProfile.getMedicalNotes(), petProfile.getOwnerName(), petProfile.getOwnerContact(),
                    petProfile.getAlternateContact()));
        }
        else if (mapping.getProfileType().getId() == 6) {
            SocialProfile socialProfile = this.socialProfileRepository.findByLinkedAccount(mapping.getUserMaster());
            resultModel.setSocialProfile(new SocialProfileResultModel(socialProfile.getFullName(), socialProfile.getNickName(),
                    socialProfile.getInstagram(), socialProfile.getFacebook(), socialProfile.getLinkedIn(), socialProfile.getTwitter(),
                    socialProfile.getEmergencyContactName(), socialProfile.getEmergencyContactNumber(), socialProfile.getMessageToFinder()));
        }
        return resultModel;
    }
}
