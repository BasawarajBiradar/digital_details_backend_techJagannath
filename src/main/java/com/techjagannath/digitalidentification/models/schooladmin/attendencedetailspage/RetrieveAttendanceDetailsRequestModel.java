package com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveAttendanceDetailsRequestModel {
    private String classLevel;
    private String division;
    private Integer roleId;
    private String dateFrom;
    private String dateTo;
    private Boolean isPresent;
}
