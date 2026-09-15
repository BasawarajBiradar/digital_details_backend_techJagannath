package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.UserMaster;

import java.time.LocalDate;

public interface StudentDetailsMasterCustomRepository {

    Integer retrieveCountOfPendingHomework(UserMaster user, LocalDate schoolStartDate);
}
