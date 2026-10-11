package com.techjagannath.digitalidentification.models.schooladmin.retrievenotices;

import com.techjagannath.digitalidentification.models.student.notice.table.NoticeRecordsFilesResultModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveNoticeTableResultModel {
    private String noticeTitle;
    private String announcementDate;
    private String noticeDetail;
    List<NoticeRecordsFilesResultModel> files;
}
