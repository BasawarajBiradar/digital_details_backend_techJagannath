package com.techjagannath.digital_identification.models.registerCards.kidsProfile;

import com.techjagannath.digital_identification.entity.UserMaster;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterCardUserKidsDetailsRequestModel {
    /* register user details */
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email id is required")
    private String emailId;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    private String alternateNumber;
    @NotBlank(message = "Address Line 1 is required")
    private String addressLineOne;
    private String addressLineTwo;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Pin code is required")
    private String pinCode;
    private String safetyNote;
    private String medicalNote;

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

    /* guardian account */
    List<RegisterCardUserKidsGuardianDetails> guardians;
}
