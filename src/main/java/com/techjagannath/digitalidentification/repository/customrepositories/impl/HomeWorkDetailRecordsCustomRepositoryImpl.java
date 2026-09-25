package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.repository.customrepositories.HomeWorkDetailRecordsCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;

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
    public Long retrieveHomeworkTotalCount(String classLevel, String division, Long userId, LocalDate schoolStartDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(id) as totalCount FROM home_work_detail_records records ")
                .append(" WHERE class_level = :classLevel AND division = :division AND records.assigned_date_and_time >= :schoolStartDate ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("classLevel", classLevel);
        query.setParameter("division", division);
        query.setParameter("schoolStartDate", schoolStartDate);
        return (Long) query.getSingleResult();
    }
}
