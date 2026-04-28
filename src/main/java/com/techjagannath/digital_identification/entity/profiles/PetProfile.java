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

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_contact")
    private String ownerContact;

    @Column(name = "owner_address")
    private String ownerAddress;

    @Column(name = "alternate_contact")
    private String alternateContact;

    @Column(name = "vaccination_status")
    private String vaccinationStatus;

    private String breed;

    // pet photo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account")
    private UserMaster linkedAccount;
}
