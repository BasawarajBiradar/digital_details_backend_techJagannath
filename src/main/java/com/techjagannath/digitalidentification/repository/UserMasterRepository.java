package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.UserMasterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> , UserMasterCustomRepository {
    UserMaster findByEmailId(String username);

    UserMaster findByUid(String uid);
}
