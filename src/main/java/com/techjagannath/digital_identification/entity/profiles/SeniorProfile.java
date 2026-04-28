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
@Table(name = "senior_profile")
public class SeniorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String gender;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "alternate_number")
    private String alternateNumber;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "medical_conditions")
    private String medicalConditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account")
    private UserMaster linkedAccount;
}