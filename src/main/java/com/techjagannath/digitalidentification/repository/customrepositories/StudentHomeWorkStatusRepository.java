package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.StudentHomeworkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentHomeWorkStatusRepository extends JpaRepository<StudentHomeworkStatus, Long> {
}
