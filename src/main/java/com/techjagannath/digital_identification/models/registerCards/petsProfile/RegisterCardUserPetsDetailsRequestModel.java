package com.techjagannath.digital_identification.models.registerCards.petsProfile;

import lombok.Data;

@Data
public class RegisterCardUserPetsDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* Pet profile */
    private String petName;
    private String species;
    private String breed;
    private String vaccinationStatus;
    private String ownerName;
    private String ownerContact;
    private String alternateContact;
    private String ownerAddress;
}
