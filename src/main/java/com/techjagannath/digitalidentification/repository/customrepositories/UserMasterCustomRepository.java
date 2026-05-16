package com.techjagannath.digitalidentification.repository.customrepositories;

import java.util.List;

public interface UserMasterCustomRepository {
    List<Object[]> retrieveStudentsListBySchool(Long schoolId, Integer size);
}
