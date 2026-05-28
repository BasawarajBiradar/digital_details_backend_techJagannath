package com.techjagannath.digitalidentification.models.schooladmin.dashboard.retrievestudentslist;

import lombok.Data;

@Data
public class RetrieveStudentsListRequestModel {
    private String classLevel;
    private String division;
    private Integer roleId;
}
