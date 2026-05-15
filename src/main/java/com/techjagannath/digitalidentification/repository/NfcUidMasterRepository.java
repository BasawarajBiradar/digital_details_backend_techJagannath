package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.NfcUidMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NfcUidMasterRepository extends JpaRepository<NfcUidMaster, Long> {
    Optional<NfcUidMaster> findByUid(String uid);
}
