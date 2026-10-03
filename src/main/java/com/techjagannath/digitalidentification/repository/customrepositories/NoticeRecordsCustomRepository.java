package com.techjagannath.digitalidentification.repository.customrepositories;

import java.util.List;

public interface NoticeRecordsCustomRepository {

    List<Object[]> retrieveNoticePageTableForStudent(Long id, String classLevel);

}
