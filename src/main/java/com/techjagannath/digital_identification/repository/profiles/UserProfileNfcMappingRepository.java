package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.profiles.UserProfileNfcMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileNfcMappingRepository extends JpaRepository<UserProfileNfcMapping, Long> {
    UserProfileNfcMapping findByUid(String uid);
}
