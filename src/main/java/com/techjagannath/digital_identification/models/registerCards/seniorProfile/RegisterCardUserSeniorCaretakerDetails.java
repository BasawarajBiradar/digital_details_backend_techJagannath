package com.techjagannath.digital_identification.models.registerCards.seniorProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCardUserSeniorCaretakerDetails {
    @NotBlank(message = "care taker name is required")
    private String careTakerName;
    private String relationship;
    @NotBlank(message = "phone number is required")
    private String phone;
    private String alternateNumber;
    private String address;
    private Boolean isPrimary;
}
