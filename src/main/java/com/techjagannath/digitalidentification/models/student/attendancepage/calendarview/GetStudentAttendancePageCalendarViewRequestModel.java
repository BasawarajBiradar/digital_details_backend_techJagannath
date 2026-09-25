package com.techjagannath.digitalidentification.models.student.attendancepage.calendarview;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetStudentAttendancePageCalendarViewRequestModel {
    private String fromDate;
    private String toDate;
    /** not to be taken from frontend */
    private LocalDate parsedFromDate;
    private LocalDate parsedToDate;
}
