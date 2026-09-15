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
@Table(name = "student_home_work_status")
public class StudentHomeworkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "student")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserMaster student;

    @JoinColumn(name = "home_work_details")
    @ManyToOne(fetch = FetchType.EAGER)
    private HomeWorkDetailRecords homeWorkDetails;

    @JoinColumn(name = "status")
    @ManyToOne(fetch = FetchType.EAGER)
    private HomeWorkStatus status;

    @Column(name = "completion_date_time")
    private LocalDateTime completionDateTime;


}
