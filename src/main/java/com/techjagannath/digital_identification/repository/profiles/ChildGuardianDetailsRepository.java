package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.profiles.ChildGuardianDetails;
import com.techjagannath.digital_identification.entity.profiles.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildGuardianDetailsRepository extends JpaRepository<ChildGuardianDetails, Long> {
    List<ChildGuardianDetails> findAllByChildProfile(ChildProfile childProfile);
}
