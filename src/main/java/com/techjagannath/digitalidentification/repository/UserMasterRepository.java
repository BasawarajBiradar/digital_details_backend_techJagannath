package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.UserMasterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> , UserMasterCustomRepository {
    UserMaster findByEmailId(String username);

    UserMaster findByUid(String uid);

    Integer countBySchoolAndRole_Id(SchoolMaster school, Integer roleId);

    @Modifying
    @Query("UPDATE UserMaster u SET u.isPresent = false WHERE u.isPresent = true")
    void resetAllUsersPresence();
}
