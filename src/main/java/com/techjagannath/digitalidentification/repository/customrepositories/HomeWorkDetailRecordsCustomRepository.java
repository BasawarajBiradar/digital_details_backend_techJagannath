package com.techjagannath.digitalidentification.repository.customrepositories;

import java.time.LocalDate;

public interface HomeWorkDetailRecordsCustomRepository {
    Long retrieveHomeworkCompletedCount(String classLevel, String division, Long userId, LocalDate schoolStartDate);

    Long retrieveHomeworkTotalCount(String classLevel, String division, Long userId, LocalDate schoolStartDate);
}
