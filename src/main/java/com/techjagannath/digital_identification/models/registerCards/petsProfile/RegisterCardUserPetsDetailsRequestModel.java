package com.techjagannath.digital_identification.models.registerCards.petsProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCardUserPetsDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* Pet profile */
    @NotBlank(message = "Pin code is required")
    private String petName;
    @NotBlank(message = "Pin code is required")
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
    @NotBlank(message = "Pin code is required")
    private String ownerName;
    @NotBlank(message = "Pin code is required")
    private String ownerContact;
    private String alternateContact;
}
