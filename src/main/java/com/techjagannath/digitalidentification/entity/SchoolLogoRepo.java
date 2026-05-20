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
@Table(name = "school_logo_repo")
public class SchoolLogoRepo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "school")
    @ManyToOne(fetch = FetchType.EAGER)
    private SchoolMaster school;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_extension")
    private String fileExtension;

    @JoinColumn(name = "uploaded_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster uploadedBy;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "is_active")
    private Boolean isActive;
}
