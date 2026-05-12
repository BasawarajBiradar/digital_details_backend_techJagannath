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
@Table(name = "user_master")
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    private String password;

    @Column(name = "email_id",  unique = true)
    private String emailId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private RoleMaster role;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school")
    private SchoolMaster school;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_details")
    private StudentDetailsMaster studentDetails; // only if user is student

    @Column(name = "uid", unique = true)
    private String uid;

    /** logging details */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private UserMaster createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
