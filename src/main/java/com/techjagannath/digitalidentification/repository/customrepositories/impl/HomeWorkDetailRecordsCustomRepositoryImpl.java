package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.repository.customrepositories.HomeWorkDetailRecordsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class HomeWorkDetailRecordsCustomRepositoryImpl implements HomeWorkDetailRecordsCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Long retrieveHomeworkCompletedCount(String classLevel, String division, Long userId, LocalDate schoolStartDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT status.home_work_details) as completionCount FROM student_home_work_status status ")
                .append(" JOIN home_work_detail_records records ON records.id = status.home_work_details ")
                .append(" WHERE student = :userId AND records.assigned_date_and_time >= :schoolStartDate ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("schoolStartDate", schoolStartDate);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long retrieveHomeworkTotalCount(String classLevel, String division, Long schoolId, LocalDate schoolStartDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(id) as totalCount FROM home_work_detail_records records ")
                .append(" WHERE records.school_master = :schoolId AND class_level = :classLevel AND division = :division AND records.assigned_date_and_time >= :schoolStartDate ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("classLevel", classLevel);
        query.setParameter("division", division);
        query.setParameter("schoolId", schoolId);
        query.setParameter("schoolStartDate", schoolStartDate);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Object[]> retrieveHomeworkTableData(Long userId, String classLevel, String division, Long schoolId, LocalDate schoolStartDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT records.id as homeworkId, records.title_or_topic, records.assigned_date_and_time, deadline_date, " +
                        " CASE WHEN status.status IS NOT NULL THEN status.status ELSE 'PENDING' END " +
                        ", subjectMaster.subject, description "+
                        "  FROM home_work_detail_records records ")
                .append(" LEFT JOIN student_home_work_status completionLogs ")
                    .append(" ON completionLogs.home_work_details = records.id AND completionLogs.student = :userId ")
                .append(" LEFT JOIN home_work_status status ON status.id = completionLogs.status ")
                .append(" LEFT JOIN subjects_master subjectMaster ON subjectMaster.id = records.subjects_master ")
                .append(" WHERE records.school_master = :schoolId " +
                        "AND records.class_level = :classLevel AND records.division = :division " +
                        "AND records.assigned_date_and_time >= :schoolStartDate ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("schoolId", schoolId);
        query.setParameter("classLevel", classLevel);
        query.setParameter("division", division);
        query.setParameter("schoolStartDate", schoolStartDate);
        return query.getResultList();
    }
}
