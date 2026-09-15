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
@Table(name = "teacher_details_master")
public class TeacherDetailsMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_teacher_of_class_level")
    private String classTeacherOfClassLevel;
    @Column(name = "class_teacher_of_division")
    private String classTeacherOfDivision;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address")
    private AddressMaster address;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "birth_date")
    private LocalDate birthDate;

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
