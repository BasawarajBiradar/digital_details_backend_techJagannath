package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.repository.customrepositories.NfcCardTapsHistoryCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NfcCardTapsHistoryRepository extends JpaRepository<NfcCardTapsHistory, Long> , NfcCardTapsHistoryCustomRepository {
}
