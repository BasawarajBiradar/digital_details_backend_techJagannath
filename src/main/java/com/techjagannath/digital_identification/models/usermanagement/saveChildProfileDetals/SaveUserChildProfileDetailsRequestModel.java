package com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveUserChildProfileDetailsRequestModel {
    private String childName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String schoolName;
    private String schoolAddress;
    private String allergies;
    private String medicalConditions;
}
