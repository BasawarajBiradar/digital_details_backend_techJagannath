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
    private String alternate_phone;
    private String guardian_name;
    private String id_proof_number;
    private String id_proof_type;
    private String is_primary;
    private String primary_phone;
    private String relationship;
}
