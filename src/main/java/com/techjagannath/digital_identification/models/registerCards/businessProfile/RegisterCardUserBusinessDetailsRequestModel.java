package com.techjagannath.digital_identification.models.registerCards.businessProfile;

import lombok.Data;

@Data
public class RegisterCardUserBusinessDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* business profile */
    private String businessName;
    private String businessDescription;
    private String ownerName;
    private String gstNumber;
    private String businessPhone;
    private String businessAddress;
    private String socialMediaLink;
    private String businessEmail;
    private String websiteUrl;
}
