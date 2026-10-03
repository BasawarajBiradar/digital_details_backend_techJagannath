package com.techjagannath.digitalidentification.repository;

import com.techjagannath.digitalidentification.entity.HomeWorkDetailsImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeWorkDetailsImagesRepository extends JpaRepository<HomeWorkDetailsImages, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM home_work_details_images images WHERE home_work_record = :homeworkId ")
    List<HomeWorkDetailsImages> findAllByHomeWorkRecordsId(Long homeworkId);
}
