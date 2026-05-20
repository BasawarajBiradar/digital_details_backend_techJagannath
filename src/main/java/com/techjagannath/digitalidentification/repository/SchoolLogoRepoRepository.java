package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.SchoolLogoRepo;
import com.techjagannath.digitalidentification.entity.SchoolMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolLogoRepoRepository extends JpaRepository<SchoolLogoRepo, Long> {
    SchoolLogoRepo findBySchoolAndIsActive(SchoolMaster school, boolean b);
}
