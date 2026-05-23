package com.techjagannath.digitalidentification.models.schooladmin.dashboard.attendencepiechart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolAdminAttendancePieChartResultModelWrapper {
    private Integer totalNumberOfStudents;
    private List<SchoolAdminAttendancePieChartResultModel> data;
}
