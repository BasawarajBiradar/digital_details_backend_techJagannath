package com.techjagannath.digitalidentification.models.teacher.addhomework;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherAddHomeworkRequestModel {
    private Integer subjectMasterId;
    private String deadlineDate;
    private LocalDate parsedDeadlineDate; // parse date in facade layer
    private String classLevel;
    private String division;
    private String homeworkTitle;
    private String description;
}
