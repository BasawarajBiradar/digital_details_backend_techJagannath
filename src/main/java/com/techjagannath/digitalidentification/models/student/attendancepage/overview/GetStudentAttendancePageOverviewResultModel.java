package com.techjagannath.digitalidentification.models.student.attendancepage.overview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentAttendancePageOverviewResultModel {
    private Double attendancePercentage;
    private Integer presentDays;
    private Integer absentDays;
    private Integer lateDays;
}
