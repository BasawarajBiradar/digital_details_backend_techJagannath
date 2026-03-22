package com.techjagannath.digital_identification.entity.profiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "senior_care_taker_details")
public class SeniorCareTakerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_taker_id")
    private Long careTakerId;

    @Column(name = "care_taker_name")
    private String careTakerName;

    private String relation;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    private String email;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private SeniorProfile seniorProfile;
}
