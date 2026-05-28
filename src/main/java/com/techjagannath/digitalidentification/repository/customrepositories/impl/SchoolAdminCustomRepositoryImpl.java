package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.repository.customrepositories.SchoolAdminCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class SchoolAdminCustomRepositoryImpl implements SchoolAdminCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Integer retrieveAttendancePieChartData(Long schoolId, Integer roleId, String classLevel, String division) {
        LocalDateTime timeStamp = LocalDate.now().atStartOfDay();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT nch.student) FROM nfc_card_taps_history nch ")
                .append(" LEFT JOIN user_master um ON um.id = nch.student ")
                .append(" LEFT JOIN nfc_reader_device_master device ON device.id = nch.device ")
                .append(" LEFT JOIN student_details_master student_details ON student_details.id = um.student_details ")
                .append(" WHERE device.school = :schoolId AND time_stamp >= :timeStamp  ");
        if (roleId != null)
            sql.append(" AND um.role = :roleId ");
        else
            sql.append(" AND um.role IN (3,4) ");
        if (classLevel != null)
            sql.append("AND student_details.class_level = :classLevel ");
        if (division != null)
            sql.append("AND student_details.division = :division ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("schoolId", schoolId);
        query.setParameter("timeStamp", timeStamp);
        if (roleId != null)
            query.setParameter("roleId", roleId);
        if (classLevel != null)
            query.setParameter("classLevel", classLevel);
        if (division != null)
            query.setParameter("division", division);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Integer totalCountOfStudents(Long schoolId, Integer roleId, String classLevel, String division) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(um.id) FROM user_master um ")
                .append(" LEFT JOIN student_details_master student_details ON student_details.id = um.student_details ")
                .append(" WHERE um.school = :schoolId ");
        if (roleId != null)
            sql.append(" AND um.role = :roleId ");
        else
            sql.append(" AND um.role IN (3,4) ");
        if (classLevel != null)
            sql.append("AND student_details.class_level = :classLevel ");
        if (division != null)
            sql.append("AND student_details.division = :division ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("schoolId", schoolId);
        if (roleId != null)
            query.setParameter("roleId", roleId);
        if (classLevel != null)
            query.setParameter("classLevel", classLevel);
        if (division != null)
            query.setParameter("division", division);

        return Integer.parseInt(query.getSingleResult().toString());
    }
}
