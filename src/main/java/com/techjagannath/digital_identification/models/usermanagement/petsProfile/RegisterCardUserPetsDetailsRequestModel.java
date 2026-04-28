package com.techjagannath.digital_identification.models.usermanagement.petsProfile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterCardUserPetsDetailsRequestModel {
    /* Pet profile */
    private String petName;
    private String species;
    private String ownerName;
    private String ownerContact;
    private String ownerAddress;
    private String alternateContact;
    private String vaccinationStatus;
    private String breed;
}
