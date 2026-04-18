package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails;

import com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveUserCardDetailsResultModel {
    private String accountType;
    private ChildProfileResultModel childProfile;
    private SeniorProfileResultModel seniorProfile;
    private BusinessProfileResultModel businessProfile;
    private VehicleProfileResultModel vehicleProfile;
    private PetProfileResultModel petProfile;
    private SocialProfileResultModel socialProfile;
}
