package com.techjagannath.digital_identification.models.usermanagement.saveChildProfileDetals;

import lombok.Data;

@Data
public class SaveUserChildProfileDetailsRequestModel {
    private String childName;
    private String gender;
    private String schoolName;
    private String schoolAddress;
}
