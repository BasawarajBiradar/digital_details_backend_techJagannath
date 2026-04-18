package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildProfileResultModel {
    private String childName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String schoolName;
    private String schoolAddress;
    private String allergies;
    private String medicalCondition;
    private List<RetrieveUserCardGuardianDetailsResultModel> guardians;
}
