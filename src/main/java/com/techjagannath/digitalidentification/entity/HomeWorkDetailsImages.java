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
@Table(name = "home_work_details_images")
public class HomeWorkDetailsImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer srNo;

    @JoinColumn(name = "school")
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolMaster school;

    @JoinColumn(name = "home_work_record")
    @ManyToOne(fetch = FetchType.EAGER)
    private HomeWorkDetailRecords homeWorkRecords;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @JoinColumn(name = "uploaded_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster uploadedBy;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

}
