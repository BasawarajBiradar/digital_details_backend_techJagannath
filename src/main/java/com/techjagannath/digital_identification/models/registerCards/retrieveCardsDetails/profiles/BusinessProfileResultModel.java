package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessProfileResultModel {
    private String businessName;
    private String businessType;
    private String registrationNumber;
    private String gstNumber;
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String websiteUrl;
    private String ownerName;
    private String ownerContact;
    private String ownerEmail;
}
