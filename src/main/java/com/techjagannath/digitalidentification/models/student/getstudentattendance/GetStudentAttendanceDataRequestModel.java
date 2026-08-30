package com.techjagannath.digitalidentification.models.student.getstudentattendance;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetStudentAttendanceDataRequestModel {
    private String fromDate;
    private String toDate;
    private String status;
    /** below parameters not to be received from frontend */
    private LocalDate parsedFromDate;
    private LocalDate parsedToDate;
}
