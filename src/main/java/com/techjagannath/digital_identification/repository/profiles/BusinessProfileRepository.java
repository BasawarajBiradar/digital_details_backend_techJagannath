package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    BusinessProfile findByLinkedAccount(UserMaster userMaster);
}
