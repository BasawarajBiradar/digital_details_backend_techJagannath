package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.AttendanceRecordsTable;
import com.techjagannath.digitalidentification.entity.RoleMaster;
import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordsTableCustomRepository {

    void populateDataInAttendanceRecordsTable(LocalDate attendanceDate);

    List<AttendanceRecordsTable> retrieveAttendanceData(LocalDate fromDate, LocalDate toDate, SchoolMaster school, UserMaster user, RoleMaster role);
}
