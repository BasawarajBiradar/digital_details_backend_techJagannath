package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.entity.AttendanceRecordsTable;
import com.techjagannath.digitalidentification.entity.RoleMaster;
import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.AttendanceRecordsTableCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AttendanceRecordsTableCustomRepositoryImpl implements AttendanceRecordsTableCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void populateDataInAttendanceRecordsTable(LocalDate attendanceDate) {

        LocalDate nextDate = attendanceDate.plusDays(1);

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO attendance_records_table ")
                .append(" (date, in_time, out_time, status, `user`, holiday_description, school, role) ");

        sql.append("SELECT :attendanceDate, ")

                // IN TIME
                .append(" CASE ")
                .append(" WHEN h.date IS NOT NULL ")
                .append("      OR DAYOFWEEK(:attendanceDate) = 1 ")
                .append(" THEN NULL ")
                .append(" ELSE TIME_FORMAT(MIN(n.time_stamp), '%H:%i:%s') ")
                .append(" END AS in_time, ")

                // OUT TIME
                .append(" CASE ")
                .append(" WHEN h.date IS NOT NULL ")
                .append("      OR DAYOFWEEK(:attendanceDate) = 1 ")
                .append(" THEN NULL ")
                .append(" ELSE TIME_FORMAT(MAX(n.time_stamp), '%H:%i:%s') ")
                .append(" END AS out_time, ")

                // STATUS
                // 1 = PRESENT
                // 2 = ABSENT
                // 3 = HOLIDAY
                .append(" CASE ")
                .append(" WHEN h.date IS NOT NULL ")
                .append("      OR DAYOFWEEK(:attendanceDate) = 1 ")
                .append(" THEN 3 ")
                .append(" WHEN MIN(n.time_stamp) IS NULL ")
                .append(" THEN 2 ")
                .append(" ELSE 1 ")
                .append(" END AS status, ")

                // USER
                .append(" u.id AS `user`, ")

                // HOLIDAY DESCRIPTION
                .append(" CASE ")
                .append(" WHEN h.date IS NOT NULL ")
                .append(" THEN h.holiday_description ")
                .append(" WHEN DAYOFWEEK(:attendanceDate) = 1 ")
                .append(" THEN 'Sunday' ")
                .append(" ELSE NULL ")
                .append(" END AS holiday_description, ")

                .append(" u.school AS school, ")

                .append(" u.role AS role ")

                // USER MASTER
                .append(" FROM user_master u ")

                // NFC TAPS FOR THE PARTICULAR DAY
                .append(" LEFT JOIN nfc_card_taps_history n ")
                .append(" ON n.student = u.id ")
                .append(" AND n.time_stamp >= :attendanceDate ")
                .append(" AND n.time_stamp < :nextDate ")

                // PUBLIC HOLIDAY
                .append(" LEFT JOIN common_public_holidays_list h ")
                .append(" ON h.date = :attendanceDate ")

                // ONLY ACTIVE USERS and Skip admin and school admin
                .append(" WHERE u.is_active and u.role NOT IN (1, 2) ")

                // GROUP PER USER
                .append(" GROUP BY ")
                .append(" u.id, ")
                .append(" h.date, ")
                .append(" h.holiday_description ");

        em.createNativeQuery(sql.toString())
                .setParameter("attendanceDate", attendanceDate)
                .setParameter("nextDate", nextDate)
                .executeUpdate();
    }

    @Override
    public List<AttendanceRecordsTable> retrieveAttendanceData(
            LocalDate fromDate, LocalDate toDate, SchoolMaster school, UserMaster user, RoleMaster role) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT obj FROM AttendanceRecordsTable obj WHERE obj.date BETWEEN :fromDate AND :toDate ");

        if (school != null)
            sql.append(" AND obj.school = :school ");
        if (role != null)
            sql.append(" AND obj.role = :role ");
        if (user != null)
            sql.append(" AND obj.user = :user ");

        sql.append("ORDER BY ");
        if (role != null)
            sql.append(" role.id, ");
        if (school != null)
            sql.append(" school.id, ");

        sql.append(" user.firstName, obj.date DESC ");

        TypedQuery<AttendanceRecordsTable> query = this.em.createQuery(sql.toString(), AttendanceRecordsTable.class);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        if (school != null)
            query.setParameter("school", school);
        if (role != null)
            query.setParameter("role", role);
        if (user != null)
            query.setParameter("user", user);

        return query.getResultList();
    }
}