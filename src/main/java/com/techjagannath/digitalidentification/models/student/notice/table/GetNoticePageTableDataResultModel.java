package com.techjagannath.digitalidentification.models.student.notice.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticePageTableDataResultModel {
    private String noticeTitle;
    private String announcementDate;
    private String noticeDetail;
    List<NoticeRecordsFilesResultModel> files;
}
