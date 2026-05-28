package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.repository.customrepositories.UserMasterCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMasterCustomRepositoryImpl implements UserMasterCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Object[]> retrieveStudentsListBySchool(Long schoolId, Integer size, Integer roleId, String classLevel, String division) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (size != null)
            sql.append(" TOP ").append(size);
        sql.append(" um.id, um.first_name, um.middle_name, um.last_name, ")
                .append(" sdm.class_level, sdm.division, um.created_at  FROM user_master um ")
                .append(" JOIN student_details_master sdm ON um.student_details  = sdm.id ")
                .append(" WHERE um.school = :schoolId ");

        if (roleId != null)
            sql.append(" AND um.role = :roleId ");
        else
            sql.append(" AND um.role IN (3, 4) ");

        if (classLevel != null)
            sql.append(" AND sdm.class_level = :classLevel ");
        if (division != null)
            sql.append(" AND sdm.division = :division ");

        sql.append("ORDER BY id DESC ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("schoolId", schoolId);
        if (roleId != null)
            query.setParameter("roleId", roleId);
        if (classLevel != null)
            query.setParameter("classLevel", classLevel);
        if (division != null)
            query.setParameter("division", division);

        return query.getResultList();
    }
}
