package com.techjagannath.digitalidentification.facade.tapaxeadmin.impl;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addnfcuid.AddNfcUidRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addnfcuid.AddNfcUidResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddLTapaxeAdminResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addtapaxeadmin.AddTapaxeAdminRequestModel;
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

    @Override
    public AddSchoolResultModel facadeEntryPointForAddSchool(HttpServletRequest request, AddSchoolRequestModel requestModel) {
        return this.tapaxeAdminService.serviceEntryPointForAddSchool(request, requestModel);
    }

    @Override
    public AddLTapaxeAdminResultModel facadeEntryPointForAddTapaxeAdmin(AddTapaxeAdminRequestModel requestModel) {
        return this.tapaxeAdminService.serviceEntryPointForAddTapaxeAdmin(requestModel);
    }

    @Override
    public AddNfcUidResultModel facadeEntryPointForAddNfcUid(HttpServletRequest request, AddNfcUidRequestModel requestModel) {
        return this.tapaxeAdminService.serviceEntryPointForAddNfcUid(request, requestModel);
    }

}
