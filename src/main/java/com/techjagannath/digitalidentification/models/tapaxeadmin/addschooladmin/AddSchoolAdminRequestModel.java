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

    private Long schoolId;
}
