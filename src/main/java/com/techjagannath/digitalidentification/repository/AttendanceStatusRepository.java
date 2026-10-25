package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Integer> {
}
