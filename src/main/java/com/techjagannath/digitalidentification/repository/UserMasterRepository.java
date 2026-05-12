package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
    UserMaster findByEmailId(String username);

    UserMaster findByUid(String uid);
}
