package com.techjagannath.digitalidentification.models.teacher.homepage.revivewrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveTeacherHomeworkReviewRequestDetailsResultModel {
    private Long reviewRequestId;
    private String studentName;
    private String homeworkTitle;
    private String homeworkDetails;
    private String homeworkAssignedDate;
    private String homeworkDeadlineDate;
    private String classLevel;
    private String division;
}
