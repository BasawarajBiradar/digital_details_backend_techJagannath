package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorProfileResultModel {
    private String fullName;
    private String gender;
    private String contactNumber;
    private String alternateNumber;
    private String bloodGroup;
    private String medicalCondition;
}
