package com.techjagannath.digitalidentification.models.student.todayupdates;

import lombok.Data;

@Data
public class RetrieveStudentHomePageTodayUpdatesResultModel {
    private String attendanceStatus;
    private String entryTime;
    private Integer tapPhotoCount;
    private Integer pendingHomeWorkCount;
    private Integer noticeCount;
    private String teacherFeedBack;
    private String weeklyPerformanceGrade;
    private Double percentage;
}
