package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveUserCardCareTakerDetailsResultModel {
    private String careTakerName;
    private String relationship;
    private String phone;
    private String alternateNumber;
    private Boolean isPrimary;
}
