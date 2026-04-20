package com.techjagannath.digital_identification.models.usermanagement.petsProfile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterCardUserPetsDetailsRequestModel {
    /* Pet profile */
    @NotBlank(message = "Pet Name is required")
    private String petName;
    @NotBlank(message = "species is required")
    private String species;
    private String breed;
    private String gender;
    private Double age;
    private String color;
    private String microchipId;
    private String vaccinationStatus;
    private String vetName;
    private String vetContact;
    private String medialNotes;
    @NotBlank(message = "Owner name is required")
    private String ownerName;
    @NotBlank(message = "Owner contact is required")
    private String ownerContact;
    private String alternateContact;
}
