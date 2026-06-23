package com.techjagannath.digitalidentification.repository.customrepositories;


import java.util.List;

public interface SchoolAdminCustomRepository {
    Integer retrieveAttendancePieChartData(Long schoolId, Integer roleId, String classLevel, String division);

    Integer totalCountOfStudents(Long school, Integer roleId, String classLevel, String division);

    List<Object[]> retreiveAttendanceDetailsPage(Long id, Integer roleId, String classLevel, String division, String dateFrom, String dateTo, Boolean isPresent);
}
