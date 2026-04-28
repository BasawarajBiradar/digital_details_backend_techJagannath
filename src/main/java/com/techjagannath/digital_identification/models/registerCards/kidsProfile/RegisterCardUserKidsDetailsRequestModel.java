package com.techjagannath.digital_identification.models.registerCards.kidsProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterCardUserKidsDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* child profile */
    @NotBlank(message = "Child name is required")
    private String childName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String schoolName;
    private String schoolAddress;
    private String allergies;
    private String medicalCondition;

    private String emergencyContactNumber;
    private String schoolPhone;

    /* guardian account */
    List<RegisterCardUserKidsGuardianDetails> guardians;
}
