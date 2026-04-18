package com.techjagannath.digital_identification.models.registerCards.retrieveCardsDetails.profiles;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeniorProfileResultModel {
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String medicalCondition;
    private String medications;
    private String doctorName;
    private String doctorContact;
    private String hospitalPreference;
    private String insuranceProvider;
    private String insuranceNumber;
    private List<RetrieveUserCardCareTakerDetailsResultModel> careTakers;
}
