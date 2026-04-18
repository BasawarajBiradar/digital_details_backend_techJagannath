package com.techjagannath.digital_identification.repository.profiles;

import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.entity.profiles.VehicleProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleProfileRepository extends JpaRepository<VehicleProfile, Long> {
    VehicleProfile findByLinkedAccount(UserMaster userMaster);
}
