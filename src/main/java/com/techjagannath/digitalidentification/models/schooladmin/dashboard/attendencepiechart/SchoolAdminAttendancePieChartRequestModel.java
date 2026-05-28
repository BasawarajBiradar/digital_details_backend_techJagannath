package com.techjagannath.digitalidentification.models.schooladmin.dashboard.attendencepiechart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolAdminAttendancePieChartRequestModel {
    private String classLevel;
    private String division;
    private Integer roleId;
}
