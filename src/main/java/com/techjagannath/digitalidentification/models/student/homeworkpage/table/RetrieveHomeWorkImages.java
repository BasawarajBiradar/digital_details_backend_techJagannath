package com.techjagannath.digitalidentification.models.student.homeworkpage.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveHomeWorkImages {
    private Integer srNo;
    private String fileUrl;
    private String fileName;
}
