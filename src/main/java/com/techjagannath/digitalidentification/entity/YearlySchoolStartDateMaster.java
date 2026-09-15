package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yearly_school_start_date_master")
public class YearlySchoolStartDateMaster {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name = "school_master")
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolMaster schoolMaster;

    @Column(name = "school_start_date")
    private LocalDate schoolStartDate;
}
