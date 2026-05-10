package com.techjagannath.digitalidentification.facade.tapaxeadmin;

import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschool.AddSchoolResultModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import jakarta.servlet.http.HttpServletRequest;

public interface TapaxeAdminFacade {
    AddSchoolAdminResultModel facadeEntryPointForAddSchoolAdmin(HttpServletRequest request, AddSchoolAdminRequestModel requestModel);

    AddSchoolResultModel facadeEntryPointForAddSchool(HttpServletRequest request, AddSchoolRequestModel requestModel);
}
