package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.entity.UserMaster;

import java.time.LocalDate;
import java.util.List;

public interface NfcCardTapsHistoryCustomRepository {
    List<NfcCardTapsHistory> retrieveLoggedInUserTodayEntries(UserMaster user);

    NfcCardTapsHistory findTodayFirstEntry(UserMaster user);

    List<Object[]> retrievePhotoTapRecordsByUser(Long userId, LocalDate parsedFromDate, LocalDate parsedToDate);
}
