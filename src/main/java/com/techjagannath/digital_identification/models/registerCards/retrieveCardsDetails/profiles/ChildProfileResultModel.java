package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildProfileResultModel {
    private String childName;
    private String gender;
    private String schoolName;
    private String schoolAddress;
    private String schoolPhone;
    private String emergencyContactNumber;
    private List<RetrieveUserCardGuardianDetailsResultModel> guardians;
}
