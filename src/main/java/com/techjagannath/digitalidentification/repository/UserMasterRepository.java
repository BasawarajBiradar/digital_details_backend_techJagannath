package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.UserMasterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> , UserMasterCustomRepository {
    UserMaster findByEmailId(String username);

    UserMaster findByUid(String uid);

    Integer countBySchoolAndRole_Id(SchoolMaster school, Integer roleId);

}
