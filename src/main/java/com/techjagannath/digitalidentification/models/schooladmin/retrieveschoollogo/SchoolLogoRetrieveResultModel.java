package com.techjagannath.digitalidentification.models.schooladmin.retrieveschoollogo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolLogoRetrieveResultModel {
    private String fileName;
    private String fileUrl;
    private String schoolName;
}
