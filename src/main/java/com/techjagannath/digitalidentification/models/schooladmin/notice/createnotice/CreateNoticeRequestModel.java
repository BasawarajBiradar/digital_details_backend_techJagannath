package com.techjagannath.digitalidentification.models.schooladmin.notice.createnotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeRequestModel {
    private String noticeTitle;
    private String noticeDescription;

    private String classLevel;
    private Boolean isStaff;
}
