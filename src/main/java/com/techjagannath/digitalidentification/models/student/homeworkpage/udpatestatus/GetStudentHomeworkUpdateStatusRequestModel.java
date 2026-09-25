package com.techjagannath.digitalidentification.models.student.homeworkpage.udpatestatus;

import lombok.Data;

@Data
public class GetStudentHomeworkUpdateStatusRequestModel {
    private Integer status;
    private Long homeworkId;
}
