package com.techjagannath.digitalidentification.models.schooladmin.attendencedetailspage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveAttendanceDetailsRequestModel {
    private String fromDate;
    private String toDate;
    private String status;
    private String classLevel;
    private String division;
    private Integer roleId;
    /** below parameters not to be received from frontend */
    private LocalDate parsedFromDate;
    private LocalDate parsedToDate;
}
