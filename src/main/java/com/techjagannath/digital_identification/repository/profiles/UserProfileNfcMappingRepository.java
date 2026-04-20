package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.UserProfileNfcMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileNfcMappingRepository extends JpaRepository<UserProfileNfcMapping, Long> {
    UserProfileNfcMapping findByUid(String uid);

    @Query(value = "SELECT TOP 1 uid FROM user_profile_nfc_mapping " +
            "WHERE uid LIKE :prefix% " +
            "ORDER BY uid DESC ", nativeQuery = true)
    String findLatestUidByPrefix(@Param("prefix") String prefix);

    List<UserProfileNfcMapping> findAllByUserMaster(UserMaster user);
}
