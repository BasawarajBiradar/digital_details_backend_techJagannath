package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileResultModel {
    private String businessName;
    private String businessDescription;
    private String ownerName;
    private String gstNumber;
    private String businessPhone;
    private String businessAddress;
    private String businessEmail;
    private String websiteUrl;
}
