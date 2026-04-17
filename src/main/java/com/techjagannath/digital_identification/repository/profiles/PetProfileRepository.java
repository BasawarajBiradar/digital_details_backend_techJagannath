package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.profiles.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetProfileRepository extends JpaRepository<PetProfile, Long> {
}
