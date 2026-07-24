package com.techjagannath.digitalidentification.models.student.todayentries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveStudentHomePageTodayEntriesResultModel {
    private String date;
    private String time;
    private String deviceId;
    private String imageUrl;
}
