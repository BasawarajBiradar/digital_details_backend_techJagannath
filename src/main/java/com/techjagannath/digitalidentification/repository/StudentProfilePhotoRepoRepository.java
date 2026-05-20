package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.StudentProfilePhotoRepo;
import com.techjagannath.digitalidentification.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfilePhotoRepoRepository extends JpaRepository<StudentProfilePhotoRepo, Long> {
    StudentProfilePhotoRepo findByMappedUserAndIsActive(UserMaster mappedUser, boolean isActive);
}
