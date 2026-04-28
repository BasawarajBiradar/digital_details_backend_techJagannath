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
    private String ownerName;
    private String ownerContact;
    private String ownerAddress;
    private String alternateContact;
    private String vaccinationStatus;
    private String breed;
}
