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
@Table(name = "student_profile_photo_repo")
public class StudentProfilePhotoRepo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "mapped_user")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserMaster mappedUser;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "is_active")
    private Boolean isActive;
}
