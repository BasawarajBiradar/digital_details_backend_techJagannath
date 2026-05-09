package com.techjagannath.digitalidentification.service.tapaxeadmin;

import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminRequestModel;
import com.techjagannath.digitalidentification.models.tapaxeadmin.addschooladmin.AddSchoolAdminResultModel;
import jakarta.servlet.http.HttpServletRequest;

public interface TapaxeAdminService {
    AddSchoolAdminResultModel serviceEntryPointForAddSchoolAdmin(HttpServletRequest request, AddSchoolAdminRequestModel requestModel);
}
