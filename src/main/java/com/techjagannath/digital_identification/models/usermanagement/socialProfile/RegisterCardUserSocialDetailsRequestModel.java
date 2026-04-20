package com.techjagannath.digital_identification.models.usermanagement.socialProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCardUserSocialDetailsRequestModel {
    /* social profile */
    @NotBlank(message = "full name is required")
    private String fullName;
    private String nickname;
    private String instagramHandle;
    private String facebookProfile;
    private String linkedinProfile;
    private String twitterHandle;
    @NotBlank(message = "emergency contact name is required")
    private String emergencyContactName;
    @NotBlank(message = "emergency contact number is required")
    private String emergencyContactNumber;
    private String messageToFinder;
}
