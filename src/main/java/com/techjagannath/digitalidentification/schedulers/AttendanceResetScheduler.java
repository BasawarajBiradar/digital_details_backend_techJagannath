package com.techjagannath.digitalidentification.schedulers;

import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceResetScheduler {

    private final UserMasterRepository userMasterRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void resetAttendance() {
        userMasterRepository.resetAllUsersPresence();
    }

}
