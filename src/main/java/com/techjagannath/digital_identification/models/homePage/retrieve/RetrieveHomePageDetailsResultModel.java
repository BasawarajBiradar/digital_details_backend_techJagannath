package com.techjagannath.digital_identification.models.homePage.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveHomePageDetailsResultModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private String alternateNumber;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String safetyNote;
    private String medicalNote;
    private List<CardsToShowResultModel> cards;
}
