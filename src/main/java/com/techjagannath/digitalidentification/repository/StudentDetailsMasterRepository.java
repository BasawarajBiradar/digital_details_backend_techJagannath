package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.StudentDetailsMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.StudentDetailsMasterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailsMasterRepository extends JpaRepository<StudentDetailsMaster, Long> , StudentDetailsMasterCustomRepository {
}
