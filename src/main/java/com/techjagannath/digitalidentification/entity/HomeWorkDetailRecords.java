package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "home_work_detail_records")
public class HomeWorkDetailRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "subjects_master")
    @ManyToOne(fetch = FetchType.EAGER)
    private SubjectsMaster subjectsMaster;

    @Column(name = "assigned_date_and_time")
    private LocalDateTime assignedDateAndTime;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "class_level")
    private String classLevel;

    private String division;

    @JoinColumn(name = "school_master")
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolMaster schoolMaster;

    @JoinColumn(name = "assigned_by")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserMaster assignedBy;

    @Column(name = "title_or_topic", length = 500)
    private String titleOrTopic;

    @Column(length = 1500)
    private String description;

}
