package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetProfileResultModel {
    private String petName;
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
    private String ownerName;
    private String ownerContact;
    private String alternateContact;
}
