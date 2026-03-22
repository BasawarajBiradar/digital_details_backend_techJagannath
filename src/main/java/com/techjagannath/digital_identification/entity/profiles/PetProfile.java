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
@Table(name = "pet_profile")
public class PetProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_name")
    private String petName;

    private String species;

    private String breed;

    private String gender;

    private Double age;

    private String colour;

    @Column(name = "micro_chip_id")
    private String microChipId;

    @Column(name = "vaccination_status")
    private String vaccinationStatus;

    @Column(name = "vet_name")
    private String vetName;

    @Column(name = "vet_contact")
    private String vetContact;

    @Column(name = "medical_notes")
    private String medicalNotes;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_contact")
    private String ownerContact;

    @Column(name = "alternate_contact")
    private String alternateContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account")
    private UserMaster linkedAccount;
}
