package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.StudentDetailsMasterCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentDetailsMasterCustomRepositoryImpl implements StudentDetailsMasterCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Long retrieveCountOfPendingHomework(UserMaster user, LocalDate schoolStartDate) {
        LocalDateTime startDateTime = schoolStartDate.atStartOfDay();

        String sql ="""
        SELECT COUNT(id) FROM home_work_detail_records WHERE assigned_date_and_time >= :schoolStartDate AND class_level = :class AND division = :div
        """;
        Query query = em.createNativeQuery(sql);
        query.setParameter("schoolStartDate", startDateTime);
        query.setParameter("class", user.getStudentDetails().getClassLevel());
        query.setParameter("div", user.getStudentDetails().getDivision());
        Long totalCount = (Long) query.getSingleResult();

        sql = """
               SELECT COUNT(id) FROM student_home_work_status WHERE student = :studentId AND completion_date_time >= :schoolStartDate
               """;
        query = em.createNativeQuery(sql);
        query.setParameter("schoolStartDate", startDateTime);
        query.setParameter("studentId", user.getId());
        Long completeCount = (Long) query.getSingleResult();

        return totalCount - completeCount;
    }
}
