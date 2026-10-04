package com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveAttendanceDetailsResult {
    private String date;
    private String status;
    private String inTime;
    private String outTime;
}
