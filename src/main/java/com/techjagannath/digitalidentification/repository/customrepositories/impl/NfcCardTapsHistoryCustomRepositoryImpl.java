package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.NfcCardTapsHistoryCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NfcCardTapsHistoryCustomRepositoryImpl implements NfcCardTapsHistoryCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<NfcCardTapsHistory> retrieveLoggedInUserTodayEntries(UserMaster user) {
        LocalDateTime timeStamp = LocalDate.now().atStartOfDay();
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT obj FROM NfcCardTapsHistory obj WHERE obj.studentUser = :user AND timeStamp >= :timeStamp ");
        TypedQuery<NfcCardTapsHistory> query = em.createQuery(jpql.toString(), NfcCardTapsHistory.class);
        query.setParameter("user", user);
        query.setParameter("timeStamp", timeStamp);
        return query.getResultList();
    }
}
