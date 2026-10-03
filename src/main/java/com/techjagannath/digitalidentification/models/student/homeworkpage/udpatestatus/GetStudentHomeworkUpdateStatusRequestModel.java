package com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus;

import lombok.Data;

@Data
public class GetStudentHomeworkUpdateStatusRequestModel {
    private Long status;
    private Long homeworkId;
}
