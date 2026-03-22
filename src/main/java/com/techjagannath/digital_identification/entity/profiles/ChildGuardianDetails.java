package com.techjagannath.digital_identification.entity.profiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "child_guardian_details")
public class ChildGuardianDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guardian_id")
    private Long guardianId;

    @Column(name = "guardian_name")
    private String guardianName;

    private String relation;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    private String email;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "id_proof_type")
    private String idProofType;

    @Column(name = "id_proof_number")
    private String idProofNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ChildProfile childProfile;
}
