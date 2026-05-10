package com.techjagannath.digitalidentification.models.tapaxeadmin.addschool;

import lombok.Data;

@Data
public class AddSchoolRequestModel {
    private String schoolName;
    private String schoolContact;
    private String schoolEmailId;

    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String pinCode;
    private String state;
    private String country;
}
