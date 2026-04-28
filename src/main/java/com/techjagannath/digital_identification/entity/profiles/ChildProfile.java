package com.techjagannath.digital_identification.entity.profiles;

import com.techjagannath.digital_identification.entity.UserMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "child_profile")
public class ChildProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name")
    private String childName;

    private String gender;

    @Column(name = "student_address")
    private String studentAddress;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_address")
    private String schoolAddress;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "school_phone")
    private String schoolPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account")
    private UserMaster linkedAccount;
}
