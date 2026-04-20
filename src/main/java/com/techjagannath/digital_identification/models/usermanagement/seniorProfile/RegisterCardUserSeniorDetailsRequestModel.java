package com.techjagannath.digital_identification.models.usermanagement.seniorProfile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisterCardUserSeniorDetailsRequestModel {
    /* senior profile */
    @NotBlank(message = "full name is required")
    private String fullName;
    @NotBlank(message = "date of birth is required")
    private LocalDate dateOfBirth;
    @NotBlank(message = "gender is required")
    private String gender;
    private String bloodGroup;
    private String medicalCondition;
    private String medications;
    private String doctorName;
    private String doctorContact;
    private String hospitalPreference;
    private String insuranceProvider;
    private String insuranceNumber;

    /* care taker account */
    List<RegisterCardUserSeniorCaretakerDetails> caretakers;

}
