package com.techjagannath.digitalidentification.repository.customrepositories.impl;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.customrepositories.NfcCardTapsHistoryCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    @Override
    public NfcCardTapsHistory findTodayFirstEntry(UserMaster user) {
        LocalDateTime timeStamp = LocalDate.now().atStartOfDay();
        StringBuilder jpql = new StringBuilder();
        jpql.append("""
        SELECT obj FROM NfcCardTapsHistory obj WHERE obj.studentUser = :user
          AND obj.timeStamp >= :timeStamp ORDER BY obj.timeStamp ASC
        """);
        TypedQuery<NfcCardTapsHistory> query = em.createQuery(jpql.toString(), NfcCardTapsHistory.class);
        query.setParameter("user", user);
        query.setParameter("timeStamp", timeStamp);
        query.setMaxResults(1);
        List<NfcCardTapsHistory> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Object[]> retrievePhotoTapRecordsByUser(Long userId, LocalDate parsedFromDate, LocalDate parsedToDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT time_stamp, file_url FROM nfc_card_taps_history records ")
                .append("WHERE time_stamp BETWEEN :fromDate AND :toDate ")
                .append("AND student = :userId ORDER BY time_stamp ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("fromDate", parsedFromDate);
        query.setParameter("toDate", parsedToDate);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
