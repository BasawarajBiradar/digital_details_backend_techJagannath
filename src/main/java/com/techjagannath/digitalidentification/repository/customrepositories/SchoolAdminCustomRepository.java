package com.techjagannath.digitalidentification.repository.customrepositories;


public interface SchoolAdminCustomRepository {
    Integer retrieveAttendancePieChartData(Long schoolId, Integer roleId, String classLevel, String division);

    Integer totalCountOfStudents(Long school, Integer roleId, String classLevel, String division);
}
