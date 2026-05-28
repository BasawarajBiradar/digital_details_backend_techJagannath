package com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveStudentsListResultModel {
    private Long id;
    private String studentName;
    private String classLevel;
    private String division;
    private String registrationDate;
    private String isPresent;
}
