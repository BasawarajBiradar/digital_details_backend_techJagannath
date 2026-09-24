package com.techjagannath.digitalidentification.models.student.attendancepage.overview;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetStudentAttendancePageOverviewRequestModel {
    private String fromDate;
    private String toDate;

    /** not to be taken from client */
    private LocalDate parsedFromDate;
    private LocalDate parsedToDate;
}
