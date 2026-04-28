package com.techjagannath.digital_identification.models.usermanagement.seniorProfile;

import lombok.Data;

@Data
public class RegisterCardUserSeniorDetailsRequestModel {
    /* senior profile */
    private String fullName;
    private String gender;
    private String bloodGroup;
    private String medicalCondition;
}
