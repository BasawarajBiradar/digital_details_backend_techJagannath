package com.techjagannath.digitalidentification.models.student.registerstudentnfc;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterStudentUidRequestModel {
    @NotBlank
    private String firstName;
    private String lastName;
    private String middleName;
    private String classLevel;
    private String division;
    private String bloodGroup;

    @NotBlank
    private LocalDate birthDate;
    private String mobileNumber;
    private String emailId;

    private String emergencyContactNumber;
    private String emergencyContactName;
    private String emergencyContactRelation;
    private String alternateNumber;

    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String pinCode;
    private String state;
    private String country;

    private Long schoolId;
}
