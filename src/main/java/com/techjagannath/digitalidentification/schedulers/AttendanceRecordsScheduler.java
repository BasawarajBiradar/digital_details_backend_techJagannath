package com.techjagannath.digitalidentification.schedulers;

import com.techjagannath.digitalidentification.repository.AttendanceRecordsTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class AttendanceRecordsScheduler {

    private final AttendanceRecordsTableRepository attendanceRecordsTableRepository;

    public void updateUsingController(LocalDate attendanceDate) {
        attendanceRecordsTableRepository.populateDataInAttendanceRecordsTable(attendanceDate);
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Kolkata")    @Transactional
    public void updateAttendanceTableRecords() {
        LocalDate attendanceDate = LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(1);
        attendanceRecordsTableRepository.populateDataInAttendanceRecordsTable(attendanceDate);
    }

}
