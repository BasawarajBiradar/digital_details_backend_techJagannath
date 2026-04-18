package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.profiles.SeniorCareTakerDetails;
import com.techjagannath.digital_identification.entity.profiles.SeniorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeniorCareTakerDetailsRepository extends JpaRepository<SeniorCareTakerDetails, Long> {
    List<SeniorCareTakerDetails> findAllBySeniorProfile(SeniorProfile seniorProfile);
}
