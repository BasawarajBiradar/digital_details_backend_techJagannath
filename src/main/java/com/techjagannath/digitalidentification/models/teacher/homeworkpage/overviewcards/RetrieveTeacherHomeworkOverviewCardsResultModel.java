package com.techjagannath.digitalidentification.models.teacher.homeworkpage.overviewcards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveTeacherHomeworkOverviewCardsResultModel {
    private Integer countThisMonth;
    private Integer reviewRequest;
}
