package com.techjagannath.digitalidentification.models.teacher.homepage.inforcard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveTeacherHomePageInfoCardDetailsResultModel {
    private String schoolName;
    private String schoolLogoUrl;

    private String photoUrl;
    private String fullName;
    private String classTeacherOfClassLevel;
    private String classTeacherOfDiv;
    private String bloodGroup;
    private String contactNumber;
    private String emailId;
    private String birthDate;
    private String address;

    private String emergencyContactName;
    private String emergencyContactNumber;
    private String emergencyContactRelation;
    private String alternateContactNumber;

    private String uid;
}
