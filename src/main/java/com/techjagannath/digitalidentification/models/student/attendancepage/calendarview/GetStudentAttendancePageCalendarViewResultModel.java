package com.techjagannath.digitalidentification.models.student.attendancepage.calendarview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentAttendancePageCalendarViewResultModel {
    private String date;
    private String status;
}
