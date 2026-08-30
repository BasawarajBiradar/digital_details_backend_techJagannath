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
@Table(name = "attendance_records_table")
public class AttendanceRecordsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @Column(name = "in_time")
    private String inTime;

    @Column(name = "out_time")
    private String outTime;

    @ManyToOne
    @JoinColumn(name = "status")
    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserMaster user;

    @Column(name = "holiday_description")
    private String holidayDescription;
}
