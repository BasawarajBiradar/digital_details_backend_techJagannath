package com.techjagannath.digitalidentification.facade.student.impl;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.models.student.homepageinfocard.RetrieveStudentHomePageInfoCardDetailsResultModel;
import com.techjagannath.digitalidentification.service.student.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;

    public StudentFacadeImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public RetrieveStudentHomePageInfoCardDetailsResultModel facadeEntryPointForRetrieveHomePageInfoCardDetails(HttpServletRequest request) {
        return this.studentService.serviceEntryPointForRetrieveHomePageInfoCardDetails(request);
    }
}
