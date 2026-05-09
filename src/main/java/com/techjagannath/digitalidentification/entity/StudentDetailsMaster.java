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
@Table(name = "student_details_master")
public class StudentDetailsMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_level")
    private String classLevel;
    private String division;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_address")
    private AddressMaster studentAddress;

    @Column(name = "blood_group")
    private String bloodGroup;


    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_relation")
    private String emergencyContactRelation;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "alternate_contact_name")
    private String alternateContactNumber;

    /** logging details */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserMaster createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
