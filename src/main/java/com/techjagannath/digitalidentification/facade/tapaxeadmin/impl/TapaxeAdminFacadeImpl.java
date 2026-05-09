package com.techjagannath.digitalidentification.facade.tapaxeadmin.impl;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import com.techjagannath.digitalidentification.service.tapaxeadmin.TapaxeAdminService;
import org.springframework.stereotype.Component;

@Component
public class TapaxeAdminFacadeImpl implements TapaxeAdminFacade {

    private final TapaxeAdminService tapaxeAdminService;

    public TapaxeAdminFacadeImpl(TapaxeAdminService tapaxeAdminService) {
        this.tapaxeAdminService = tapaxeAdminService;
    }

}
