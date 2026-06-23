package com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveAttendanceDetailsResultModel {
    private String fullName;
    private String classLevel;
    private String division;
    private String date;
    private Boolean status;
}
