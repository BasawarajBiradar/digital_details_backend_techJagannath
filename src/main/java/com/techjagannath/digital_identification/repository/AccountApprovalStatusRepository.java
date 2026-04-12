package com.techjagannath.digital_identification.repository;

import com.techjagannath.digital_identification.entity.AccountApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountApprovalStatusRepository extends JpaRepository<AccountApprovalStatus, Integer> {
}
