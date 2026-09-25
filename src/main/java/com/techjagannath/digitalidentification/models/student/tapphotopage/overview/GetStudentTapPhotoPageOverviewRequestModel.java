package com.techjagannath.digitalidentification.models.student.tapphotopage.overview;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetStudentTapPhotoPageOverviewRequestModel {
    private String fromDate;
    private String toDate;
    private LocalDate parsedFromDate;
    private LocalDate parsedToDate;
}
