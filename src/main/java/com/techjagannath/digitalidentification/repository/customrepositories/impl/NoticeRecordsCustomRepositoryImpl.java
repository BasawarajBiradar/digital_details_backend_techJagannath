package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.repository.customrepositories.NoticeRecordsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRecordsCustomRepositoryImpl implements NoticeRecordsCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Object[]> retrieveNoticePageTableForStudent(Long schoolId, String classLevel) {
        String sql =
                new String("""
                        SELECT record.notice_title, record.notice_description, record.announcement_date, record.id AS noticeRecordId\s
                         FROM notice_records record WHERE record.school_master = :schoolId\s
                         AND (record.class_level IS NULL OR record.class_level = :classLevel)
                         AND is_staff = 0
                         ORDER BY record.announcement_date DESC """);
        Query query = em.createNativeQuery(sql);
        query.setParameter("schoolId", schoolId);
        query.setParameter("classLevel", classLevel);
        return query.getResultList();
    }

    @Override
    public List<Object[]> retrieveNoticePageTableForTeacher(Long schoolId) {
        String sql =
                new String("""
                        SELECT record.notice_title, record.notice_description, record.announcement_date, record.id AS noticeRecordId\s
                         FROM notice_records record WHERE record.school_master = :schoolId\s
                         AND is_staff = 1
                         ORDER BY record.announcement_date DESC """);
        Query query = em.createNativeQuery(sql);
        query.setParameter("schoolId", schoolId);
        return query.getResultList();
    }
}
