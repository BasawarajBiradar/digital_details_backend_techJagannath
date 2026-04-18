package com.techjagannath.digital_identification.models.registerCards.kidsProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCardUserKidsGuardianDetails {
    private String address;
    private String email;
    private String alternatePhone;
    private String guardianName;
    private String idProofNumber;
    private String idProofType;
    private String isPrimary;
    private String primaryPhone;
    private String relationship;
}
