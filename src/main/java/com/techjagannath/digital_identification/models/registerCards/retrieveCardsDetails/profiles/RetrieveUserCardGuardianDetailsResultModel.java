package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveUserCardGuardianDetailsResultModel {
    private String email;
    private String alternatePhone;
    private String guardianName;
    private String idProofNumber;
    private String idProofType;
    private Boolean isPrimary;
    private String primaryPhone;
    private String relationship;
}
