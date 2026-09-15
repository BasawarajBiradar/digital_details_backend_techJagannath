package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.UserMaster;

import java.time.LocalDate;

public interface StudentDetailsMasterCustomRepository {

    Long retrieveCountOfPendingHomework(UserMaster user, LocalDate schoolStartDate);
}
