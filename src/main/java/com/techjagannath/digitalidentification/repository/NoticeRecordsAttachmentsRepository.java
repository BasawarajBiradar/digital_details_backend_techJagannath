package com.techjagannath.digitalidentification.repository;


import com.techjagannath.digitalidentification.entity.NoticeRecordsAttachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRecordsAttachmentsRepository extends JpaRepository<NoticeRecordsAttachments, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM notice_records_attachments files WHERE notice_records = :noticeId ")
    List<NoticeRecordsAttachments> findAllByNoticeRecordsId(Long noticeId);
}
