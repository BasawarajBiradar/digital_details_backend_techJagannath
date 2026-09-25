package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.HomeWorkDetailRecords;
import com.techjagannath.digitalidentification.entity.StudentHomeworkStatus;
import com.techjagannath.digitalidentification.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentHomeWorkStatusRepository extends JpaRepository<StudentHomeworkStatus, Long> {

    StudentHomeworkStatus findByHomeWorkDetailsAndStudent(HomeWorkDetailRecords homework, UserMaster user);
}
