package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.AttendanceRecordsTable;
import com.techjagannath.digitalidentification.repository.customrepositories.AttendanceRecordsTableCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AttendanceRecordsTableRepository extends
        JpaRepository<AttendanceRecordsTable, Long>, AttendanceRecordsTableCustomRepository {

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM attendance_records_table WHERE date BETWEEN :fromDate AND :toDate AND status = :status ;")
    Integer retrieveCountByStatusBetweenDates(int status, LocalDate fromDate, LocalDate toDate);
}
