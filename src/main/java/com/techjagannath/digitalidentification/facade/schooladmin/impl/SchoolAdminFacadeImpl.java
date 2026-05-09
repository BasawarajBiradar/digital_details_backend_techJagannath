package com.techjagannath.digitalidentification.facade.schooladmin.impl;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import com.techjagannath.digitalidentification.service.schooladmin.SchoolAdminService;
import org.springframework.stereotype.Component;

@Component
public class SchoolAdminFacadeImpl implements SchoolAdminFacade {

    private final SchoolAdminService schoolAdminService;

    public SchoolAdminFacadeImpl(SchoolAdminService schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }



}
