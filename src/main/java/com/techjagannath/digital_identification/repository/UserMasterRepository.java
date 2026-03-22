package com.techjagannath.digital_identification.repository;

import com.techjagannath.digital_identification.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
}
