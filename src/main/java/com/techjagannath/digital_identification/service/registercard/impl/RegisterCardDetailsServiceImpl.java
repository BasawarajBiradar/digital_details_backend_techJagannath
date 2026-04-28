package com.techjagannath.digital_identification.service.registercard.impl;

import com.techjagannath.digital_identification.entity.ProfileTypesMaster;
import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.*;
import com.techjagannath.digital_identification.exception.ResourceNotFoundException;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.businessProfile.RegisterCardUserBusinessDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.kidsProfile.RegisterCardUserKidsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.petsProfile.RegisterCardUserPetsDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.RetrieveUserCardDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles.*;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.seniorProfile.RegisterCardUserSeniorDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.socialProfile.RegisterCardUserSocialDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.validateUserDetails.ValidateUserDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.validateUserDetails.ValidateUserDetailsResultModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsRequestModel;
import com.techjagannath.digital_identification.models.registerCards.vehicleProfile.RegisterCardUserVehicleDetailsResultModel;
import com.techjagannath.digital_identification.repository.*;
import com.techjagannath.digital_identification.repository.profiles.*;
import com.techjagannath.digital_identification.service.registercard.RegisterCardDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class RegisterCardDetailsServiceImpl implements RegisterCardDetailsService {

    private final UserMasterRepository userMasterRepository;
    private final ChildProfileRepository childProfileRepository;
    private final RoleMasterRepository roleMasterRepository;
    private final SeniorProfileRepository seniorProfileRepository;
    private final BusinessProfileRepository businessProfileRepository;
    private final VehicleProfileRepository vehicleProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final PetProfileRepository petProfileRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final UserProfileNfcMappingRepository userProfileNfcMappingRepository;
    private final ProfileTypesMasterRepository profileTypesMasterRepository;
    @Autowired
    private AuthenticationManager authManager;

    public RegisterCardDetailsServiceImpl(UserMasterRepository userMasterRepository, ChildProfileRepository childProfileRepository, PasswordEncoder passwordEncoder
                                          , RoleMasterRepository roleMasterRepository, SeniorProfileRepository seniorProfileRepository,
                                          BusinessProfileRepository businessProfileRepository, VehicleProfileRepository vehicleProfileRepository,
                                          PetProfileRepository petProfileRepository, SocialProfileRepository socialProfileRepository,
                                          UserProfileNfcMappingRepository userProfileNfcMappingRepository, ProfileTypesMasterRepository profileTypesMasterRepository) {
        this.userMasterRepository = userMasterRepository;
        this.passwordEncoder = passwordEncoder;
        this.childProfileRepository = childProfileRepository;
        this.roleMasterRepository = roleMasterRepository;
        this.seniorProfileRepository = seniorProfileRepository;
        this.businessProfileRepository = businessProfileRepository;
        this.vehicleProfileRepository = vehicleProfileRepository;
        this.petProfileRepository = petProfileRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.userProfileNfcMappingRepository = userProfileNfcMappingRepository;
        this.profileTypesMasterRepository = profileTypesMasterRepository;
    }

    @Override
    public ValidateUserDetailsResultModel serviceEntryPointForValidateUserDetails(String uid, ValidateUserDetailsRequestModel requestModel) {
        if (this.userProfileNfcMappingRepository.findByUid(uid) != null)
            throw new DataIntegrityViolationException("uid already exists mapped to a profile");

        /* change this to email verification */
        UserMaster isPresentUser = this.userMasterRepository.findByEmailId(requestModel.getEmailId());
        if (isPresentUser != null) {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestModel.getEmailId(),
                            requestModel.getPassword()
                    )
            );
            return new ValidateUserDetailsResultModel(isPresentUser.getId());
        }

        UserMaster user = new UserMaster();
        if (userMasterRepository.findByEmailId(requestModel.getEmailId()) != null)
            throw new DataIntegrityViolationException("Email already exists");
        user.setFirstName(requestModel.getFirstName());
        user.setLastName(requestModel.getLastName());
        user.setEmailId(requestModel.getEmailId());
        user.setMobileNumber(requestModel.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(
                passwordEncoder.encode(requestModel.getPassword())
        );
        user.setRole(this.roleMasterRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Default role not configured")));
        user.setIsActive(true);
        UserMaster savedUser = this.userMasterRepository.save(user);
        return new ValidateUserDetailsResultModel(savedUser.getId());
    }

    @Override
    @Transactional
    public RegisterCardUserKidsDetailsResultModel serviceEntryPointForRegisterCardUserKidsDetails(String uid, RegisterCardUserKidsDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        /* child details */
        ChildProfile childProfile = new ChildProfile();
        childProfile.setChildName(requestModel.getChildName());
        childProfile.setGender(requestModel.getGender());
        childProfile.setSchoolName(requestModel.getSchoolName());
        childProfile.setSchoolAddress(requestModel.getSchoolAddress());
        childProfile.setEmergencyContactNumber(requestModel.getEmergencyContactNumber());
        childProfile.setSchoolPhone(requestModel.getSchoolPhone());
        childProfile.setStudentAddress(requestModel.getStudentAddress());
        childProfile.setLinkedAccount(savedUser);
        ChildProfile savedChildProfile = this.childProfileRepository.save(childProfile);
        /* update profile user mapping */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedChildProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);
        return new RegisterCardUserKidsDetailsResultModel(childProfile.getChildName());
    }

    @Override
    @Transactional
    public RegisterCardUserSeniorDetailsResultModel serviceEntryPointForRegisterCardUserSeniorDetails(String uid, RegisterCardUserSeniorDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        /* senior details */
        SeniorProfile seniorProfile = new SeniorProfile();
        seniorProfile.setFullName(requestModel.getFullName());
        seniorProfile.setGender(requestModel.getGender());
        seniorProfile.setContactNumber(requestModel.getContactNumber());
        seniorProfile.setAlternateNumber(requestModel.getAlternateNumber());
        seniorProfile.setBloodGroup(requestModel.getBloodGroup());
        seniorProfile.setMedicalConditions(requestModel.getMedicalCondition());
        seniorProfile.setLinkedAccount(savedUser);
        SeniorProfile savedSeniorProfile = this.seniorProfileRepository.save(seniorProfile);

        /* updated profile user mapping */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(2).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedSeniorProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);

        return new RegisterCardUserSeniorDetailsResultModel(savedSeniorProfile.getFullName());
    }

    @Override
    @Transactional
    public RegisterCardUserBusinessDetailsResultModel serviceEntryPointForRegisterCardUserBusinessDetails(String uid, RegisterCardUserBusinessDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        /* business details */
        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setBusinessName(requestModel.getBusinessName());
        businessProfile.setBusinessDescription(requestModel.getBusinessDescription());
        businessProfile.setOwnerName(requestModel.getOwnerName());
        businessProfile.setGstNumber(requestModel.getGstNumber());
        businessProfile.setBusinessPhone(requestModel.getBusinessPhone());
        businessProfile.setBusinessAddress(requestModel.getBusinessAddress());
        businessProfile.setSocialMediaLinks(requestModel.getSocialMediaLink());
        businessProfile.setBusinessEmail(requestModel.getBusinessEmail());
        businessProfile.setWebsiteUrl(requestModel.getWebsiteUrl());
        businessProfile.setLinkedAccount(savedUser);
        BusinessProfile savedBusinessProfile = this.businessProfileRepository.save(businessProfile);

        /* updated user profile mapping  */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(3).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedBusinessProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);

        return new RegisterCardUserBusinessDetailsResultModel(savedBusinessProfile.getBusinessName());
    }

    @Override
    @Transactional
    public RegisterCardUserVehicleDetailsResultModel serviceEntryPointForRegisterCardUserVehicleDetails(String uid, RegisterCardUserVehicleDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        /* vehicle details */
        VehicleProfile vehicleProfile = new VehicleProfile();
        vehicleProfile.setVehicleNumber(requestModel.getVehicleNumber());
        vehicleProfile.setVehicleType(requestModel.getVehicleType());
        vehicleProfile.setBrand(requestModel.getBrand());
        vehicleProfile.setModel(requestModel.getModel());
        vehicleProfile.setOwnerName(requestModel.getOwnerName());
        vehicleProfile.setOwnerContact(requestModel.getOwnerContact());
        vehicleProfile.setAlternateContact(requestModel.getAlternateContact());
        vehicleProfile.setLinkedAccount(savedUser);
        VehicleProfile savedVehicleProfile = this.vehicleProfileRepository.save(vehicleProfile);

        /* updated user profile mapping */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(4).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedVehicleProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);

        return new RegisterCardUserVehicleDetailsResultModel(savedVehicleProfile.getVehicleNumber());
    }

    @Override
    @Transactional
    public RegisterCardUserPetsDetailsResultModel serviceEntryPointForRegisterCardUserPetDetails(String uid, RegisterCardUserPetsDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        /* pet details */
        PetProfile petProfile = new PetProfile();
        petProfile.setPetName(requestModel.getPetName());
        petProfile.setSpecies(requestModel.getSpecies());
        petProfile.setOwnerName(requestModel.getOwnerName());
        petProfile.setOwnerContact(requestModel.getOwnerContact());
        petProfile.setOwnerAddress(requestModel.getOwnerAddress());
        petProfile.setAlternateContact(requestModel.getAlternateContact());
        petProfile.setVaccinationStatus(requestModel.getVaccinationStatus());
        petProfile.setBreed(requestModel.getBreed());
        petProfile.setLinkedAccount(savedUser);
        PetProfile savedPetProfile = this.petProfileRepository.save(petProfile);

        /* udpdated user profile mapping */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(5).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedPetProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);

        return new RegisterCardUserPetsDetailsResultModel(savedPetProfile.getPetName());
    }

    @Override
    @Transactional
    public RegisterCardUserSocialDetailsResultModel serviceEntryPointForRegisterCardUserSocialDetails(String uid, RegisterCardUserSocialDetailsRequestModel requestModel) {
        /* user details */
        UserMaster savedUser = this.userMasterRepository.findById(requestModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));

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

        /* updated user profile mapping */
        ProfileTypesMaster profileType = this.profileTypesMasterRepository.findById(6).orElseThrow(() -> new ResourceNotFoundException("Profile type not found"));
        UserProfileNfcMapping mapping = new UserProfileNfcMapping(null, savedUser, profileType, uid, savedSocialProfile.getId());
        this.userProfileNfcMappingRepository.save(mapping);

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
            ChildProfile childProfile = this.childProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("user not found"));
            resultModel.setChildProfile(new ChildProfileResultModel(
                    childProfile.getChildName(), childProfile.getGender(), childProfile.getStudentAddress(), childProfile.getSchoolName(),
                    childProfile.getSchoolAddress(), childProfile.getSchoolPhone(), childProfile.getEmergencyContactNumber()
            ));
        }

        else if (mapping.getProfileType().getId() == 2) {
            SeniorProfile seniorProfile = this.seniorProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            resultModel.setSeniorProfile(new SeniorProfileResultModel(
                    seniorProfile.getFullName(), seniorProfile.getGender(), seniorProfile.getContactNumber(), seniorProfile.getAlternateNumber(),
                    seniorProfile.getBloodGroup(), seniorProfile.getMedicalConditions()
            ));

        }

        else if (mapping.getProfileType().getId() == 3) {
            BusinessProfile businessProfile = this.businessProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            resultModel.setBusinessProfile(new BusinessProfileResultModel(
                    businessProfile.getBusinessName(), businessProfile.getBusinessDescription(), businessProfile.getOwnerName(), businessProfile.getGstNumber(),
                    businessProfile.getBusinessPhone(), businessProfile.getBusinessAddress(), businessProfile.getBusinessEmail(), businessProfile.getWebsiteUrl()
            ));
        }

        else if (mapping.getProfileType().getId() == 4) {
            VehicleProfile vehicleProfile = this.vehicleProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            resultModel.setVehicleProfile(new VehicleProfileResultModel(
                    vehicleProfile.getVehicleNumber(), vehicleProfile.getVehicleType(), vehicleProfile.getBrand(), vehicleProfile.getModel(),
                    vehicleProfile.getOwnerName(), vehicleProfile.getOwnerContact(), vehicleProfile.getAlternateContact()
            ));
        }

        else if (mapping.getProfileType().getId() == 5) {
            PetProfile petProfile = this.petProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            resultModel.setPetProfile(new PetProfileResultModel(
                    petProfile.getPetName(), petProfile.getSpecies(), petProfile.getOwnerName(), petProfile.getOwnerContact(), petProfile.getOwnerAddress()
                    , petProfile.getAlternateContact(), petProfile.getVaccinationStatus(), petProfile.getBreed()
            ));
        }
        else if (mapping.getProfileType().getId() == 6) {
            SocialProfile socialProfile = this.socialProfileRepository.findById(mapping.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            resultModel.setSocialProfile(new SocialProfileResultModel(socialProfile.getFullName(), socialProfile.getNickName(),
                    socialProfile.getInstagram(), socialProfile.getFacebook(), socialProfile.getLinkedIn(), socialProfile.getTwitter(),
                    socialProfile.getEmergencyContactName(), socialProfile.getEmergencyContactNumber(), socialProfile.getMessageToFinder()));
        }
        return resultModel;
    }
}
