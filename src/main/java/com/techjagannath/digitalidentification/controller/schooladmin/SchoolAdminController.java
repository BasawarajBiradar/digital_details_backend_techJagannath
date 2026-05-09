package com.techjagannath.digitalidentification.controller.schooladmin;

import com.techjagannath.digitalidentification.facade.schooladmin.SchoolAdminFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminController {

    private final SchoolAdminFacade schoolAdminFacade;

    public SchoolAdminController(SchoolAdminFacade schoolAdminFacade) {
        this.schoolAdminFacade = schoolAdminFacade;
    }

}
