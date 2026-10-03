package com.techjagannath.digitalidentification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice_records")
public class NoticeRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "school_master")
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolMaster schoolMaster;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_description")
    private String noticeDescription;

    @Column(name = "announcement_date")
    private LocalDateTime announcementDate;

    @Column(name = "class_level")
    private String classLevel;
}
