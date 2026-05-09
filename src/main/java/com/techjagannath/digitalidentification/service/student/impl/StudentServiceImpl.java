package com.techjagannath.digitalidentification.service.student.impl;

import com.techjagannath.digitalidentification.entity.SchoolMaster;
import com.techjagannath.digitalidentification.entity.StudentDetailsMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.service.student.StudentService;
import com.techjagannath.digitalidentification.utils.CommonMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class StudentServiceImpl implements StudentService {

    private final CommonMethods commonMethods;

    public StudentServiceImpl(CommonMethods commonMethods) {
        this.commonMethods = commonMethods;
    }

    @Override
    public RetrieveStudentHomePageInfoCardDetailsResultModel serviceEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        UserMaster user = this.commonMethods.extractUser(request);
        StringBuilder fullName = new StringBuilder(user.getFirstName());
        if (user.getMiddleName() != null)
            fullName.append(user.getMiddleName());
        if (user.getLastName() != null)
            fullName.append(user.getLastName());

        SchoolMaster school = user.getSchool();
        StudentDetailsMaster student = user.getStudentDetails();

        return new RetrieveStudentHomePageInfoCardDetailsResultModel(
                school.getSchoolName(), null, null, fullName.toString(), student.getClassLevel(), student.getDivision(),
                student.getBloodGroup(), student.getEmergencyContactName(), student.getEmergencyContactNumber(), student.getEmergencyContactRelation(),
                student.getAlternateContactNumber()
        );
    }
}
