package com.techjagannath.digitalidentification.repository.customrepositories;

import java.time.LocalDate;
import java.util.List;

public interface HomeWorkDetailRecordsCustomRepository {
    Long retrieveHomeworkCompletedCount(String classLevel, String division, Long userId, LocalDate schoolStartDate);

    Long retrieveHomeworkTotalCount(String classLevel, String division, Long schoolId, LocalDate schoolStartDate);

    List<Object[]> retrieveHomeworkTableData(Long userId, String classLevel, String division, Long schoolId, LocalDate schoolStartDate);
}
