package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.YearlySchoolStartDateMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearlySchoolStartDateMasterRepository extends JpaRepository<YearlySchoolStartDateMaster, Integer> {

    YearlySchoolStartDateMaster findFirstBySchoolMasterOrderBySchoolStartDateDesc(SchoolMaster school);
}
