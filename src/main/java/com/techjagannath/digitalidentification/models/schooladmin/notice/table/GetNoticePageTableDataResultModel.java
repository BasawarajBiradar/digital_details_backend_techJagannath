package com.techjagannath.digitalidentification.models.schooladmin.notice.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticePageTableDataResultModel {
    private String noticeTitle;
    private String announcementDate;
    private String noticeDetail;
}
