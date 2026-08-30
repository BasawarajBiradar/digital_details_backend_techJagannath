package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.AttendanceRecordsTable;
import com.techjagannath.digitalidentification.repository.customrepositories.AttendanceRecordsTableCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordsTableRepository extends
        JpaRepository<AttendanceRecordsTable, Long>, AttendanceRecordsTableCustomRepository {
}
