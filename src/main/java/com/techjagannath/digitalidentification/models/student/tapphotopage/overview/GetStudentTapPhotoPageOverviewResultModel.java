package com.techjagannath.digitalidentification.models.student.tapphotopage.overview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentTapPhotoPageOverviewResultModel {
    private String date;
    private String time;
    private String photoUrl;
}
