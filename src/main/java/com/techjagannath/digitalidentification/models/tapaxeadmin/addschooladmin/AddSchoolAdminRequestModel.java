package com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSchoolAdminRequestModel {
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNo;
    private String emailId;
    private String password;

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
