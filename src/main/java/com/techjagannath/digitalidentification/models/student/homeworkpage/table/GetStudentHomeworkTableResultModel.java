package com.techjagannath.digitalidentification.models.student.homeworkpage.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentHomeworkTableResultModel {
    private Long homeworkId;
    private String homeworkTitle;
    private String dateOfAssignment;
    private String deadlineDate;
    private String status;
    private String subjectName;
}
