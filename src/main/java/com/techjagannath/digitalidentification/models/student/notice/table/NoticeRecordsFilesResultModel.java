package com.techjagannath.digitalidentification.models.student.notice.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRecordsFilesResultModel {
    private Integer srNo;
    private String fileUrl;
    private String fileName;
}
