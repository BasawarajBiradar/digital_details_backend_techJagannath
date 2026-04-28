package com.techjagannath.digital_identification.models.registerCards.kidsProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RegisterCardUserKidsDetailsRequestModel {
    /* register user details */
    private Long userId;

    /* child profile */
    @NotBlank(message = "Child name is required")
    private String childName;
    private String gender;
    private String schoolName;
    private String schoolAddress;
    private String studentAddress;
    private String emergencyContactNumber;
    private String schoolPhone;
}
