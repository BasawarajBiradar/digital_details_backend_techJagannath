package com.techjagannath.digitalidentification.models.student.homeworkpage.overview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentHomeworkOverviewResultModel {
    private Long pendingHomework;
    private Long completedHomework;
}
