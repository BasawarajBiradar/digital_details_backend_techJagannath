package com.techjagannath.digitalidentification.models.teacher.register;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterTeacherUidRequestModel {
    @NotBlank
    private String firstName;
    private String lastName;
    private String middleName;
    private String classTeacherOfClassLevel;
    private String classTeacherOfDivision;
    private String bloodGroup;

    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    private String mobileNumber;
    @NotBlank
    private String emailId;

    @NotBlank
    private String password;

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
