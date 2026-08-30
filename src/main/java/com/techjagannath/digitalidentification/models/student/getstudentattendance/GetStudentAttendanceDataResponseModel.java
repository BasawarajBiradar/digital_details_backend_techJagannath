package com.techjagannath.digitalidentification.models.student.getstudentattendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentAttendanceDataResponseModel {
    private String date;
    private String status;
    private String entryTime;
    private String exitTime;
}
