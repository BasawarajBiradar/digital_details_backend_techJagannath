package com.techjagannath.digitalidentification.facade.student.impl;

import com.techjagannath.digitalidentification.facade.student.StudentFacade;
import com.techjagannath.digitalidentification.service.student.StudentService;
import org.springframework.stereotype.Component;

@Component
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;

    public StudentFacadeImpl(StudentService studentService) {
        this.studentService = studentService;
    }
}
