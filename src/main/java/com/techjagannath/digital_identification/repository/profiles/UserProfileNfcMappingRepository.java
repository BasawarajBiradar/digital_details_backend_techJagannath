package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.profiles.UserProfileNfcMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserProfileNfcMappingRepository extends JpaRepository<UserProfileNfcMapping, Long> {
    UserProfileNfcMapping findByUid(String uid);

    @Query(value = "SELECT uid FROM user_profile_nfc_mapping " +
            "WHERE uid LIKE :prefix% " +
            "ORDER BY uid DESC LIMIT 1", nativeQuery = true)
    String findLatestUidByPrefix(@Param("prefix") String prefix);
}
