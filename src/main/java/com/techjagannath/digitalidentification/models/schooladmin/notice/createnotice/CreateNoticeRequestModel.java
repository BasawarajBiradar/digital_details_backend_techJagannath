package com.techjagannath.digitalidentification.models.schooladmin.notice.createnotice;

import lombok.Data;

@Data
public class CreateNoticeRequestModel {
    private String noticeTitle;
    private String noticeDescription;
    private String classLevel;
}
