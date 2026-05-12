package com.techjagannath.digitalidentification.repository.customrepositories;

import com.techjagannath.digitalidentification.entity.NfcCardTapsHistory;
import com.techjagannath.digitalidentification.entity.UserMaster;

import java.util.List;

public interface NfcCardTapsHistoryCustomRepository {
    List<NfcCardTapsHistory> retrieveLoggedInUserTodayEntries(UserMaster user);
}
