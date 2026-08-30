package com.techjagannath.digitalidentification.repository.customrepositories;

import java.time.LocalDate;

public interface AttendanceRecordsTableCustomRepository {

    void populateDataInAttendanceRecordsTable(LocalDate attendanceDate);

}
