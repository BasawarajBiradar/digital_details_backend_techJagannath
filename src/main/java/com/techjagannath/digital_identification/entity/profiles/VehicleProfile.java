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
@Table(name = "vehicle_profile")
public class VehicleProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "vehicle_type")
    private String vehicleType;

    private String brand;

    private String model;

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
