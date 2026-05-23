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
    public Integer retrieveAttendancePieChartData(Long schoolId) {
        LocalDateTime timeStamp = LocalDate.now().atStartOfDay();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT nch.student) FROM nfc_card_taps_history nch ")
                .append(" LEFT JOIN user_master um ON um.id = nch.student ")
                .append(" LEFT JOIN nfc_reader_device_master device ON device.id = nch.device ")
                .append(" WHERE um.role = 3 AND device.school = :schoolId AND time_stamp >= :timeStamp  ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("schoolId", schoolId);
        query.setParameter("timeStamp", timeStamp);
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
