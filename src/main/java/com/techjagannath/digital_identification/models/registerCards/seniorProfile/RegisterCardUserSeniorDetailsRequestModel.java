package com.techjagannath.digital_identification.models.registerCards.seniorProfile;
import lombok.Data;

@Data
public class RegisterCardUserSeniorDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* senior profile */
    private String fullName;
    private String gender;
    private String bloodGroup;
    private String medicalCondition;
    private String contactNumber;
    private String alternateNumber;
}
