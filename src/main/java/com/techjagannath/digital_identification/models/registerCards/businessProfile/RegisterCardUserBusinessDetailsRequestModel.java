package com.techjagannath.digital_identification.models.registerCards.businessProfile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCardUserBusinessDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* business profile */
    @NotBlank(message = "business name is required")
    private String businessName;
    @NotBlank(message = "business type is required")
    private String businessType;
    private String registrationNumber;
    private String gstNumber;
    @Email(message = "Invalid business email format")
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String websiteUrl;
    @NotBlank(message = "owner name is required")
    private String ownerName;
    private String ownerContact;
    @Email(message = "Invalid owner email format")
    private String ownerEmail;
}
