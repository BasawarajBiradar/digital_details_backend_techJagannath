package com.techjagannath.digitalidentification.facade.tapaxeadmin.impl;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.service.tapaxeadmin.TapaxeAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class TapaxeAdminFacadeImpl implements TapaxeAdminFacade {

    private final TapaxeAdminService tapaxeAdminService;

    public TapaxeAdminFacadeImpl(TapaxeAdminService tapaxeAdminService) {
        this.tapaxeAdminService = tapaxeAdminService;
    }

    @Override
    public AddSchoolAdminResultModel facadeEntryPointForAddSchoolAdmin(HttpServletRequest request, AddSchoolAdminRequestModel requestModel) {
        return this.tapaxeAdminService.serviceEntryPointForAddSchoolAdmin(request, requestModel);
    }
}
