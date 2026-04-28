package com.techjagannath.digital_identification.models.usermanagement.businessProfile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterCardUserBusinessDetailsRequestModel {
    /* business profile */
    private String businessName;
    private String businessType;
    private String gstNumber;
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String websiteUrl;
    private String ownerName;
}
