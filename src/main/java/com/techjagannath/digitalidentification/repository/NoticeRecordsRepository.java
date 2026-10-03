package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.NoticeRecords;
import com.techjagannath.digitalidentification.repository.customrepositories.NoticeRecordsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRecordsRepository extends JpaRepository<NoticeRecords, Long>, NoticeRecordsCustomRepository {

}
