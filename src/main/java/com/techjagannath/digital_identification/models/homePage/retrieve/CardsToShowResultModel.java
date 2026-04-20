package com.techjagannath.digital_identification.models.homePage.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsToShowResultModel {
    private String id;
    private Boolean isActive;
    private String uid;
}
