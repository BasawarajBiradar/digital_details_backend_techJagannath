package com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentbyid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveStudentByIdResultModel {
    private String photoUrl;
    private String fullName;
    private String classLevel;
    private String div;
    private String bloodGroup;
    private String contactNumber;
    private String emailId;
    private String birthDate;
    private String address;

    private String emergencyContactName;
    private String emergencyContactNumber;
    private String emergencyContactRelation;
    private String alternateContactNumber;
}
