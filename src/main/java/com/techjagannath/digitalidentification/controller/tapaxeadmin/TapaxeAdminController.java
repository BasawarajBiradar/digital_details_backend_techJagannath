package com.techjagannath.digitalidentification.controller.tapaxeadmin;

import com.techjagannath.digitalidentification.facade.tapaxeadmin.TapaxeAdminFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tapaxe-admin")
public class TapaxeAdminController {

    private final TapaxeAdminFacade tapaxeAdminFacade;

    public TapaxeAdminController(TapaxeAdminFacade tapaxeAdminFacade) {
        this.tapaxeAdminFacade = tapaxeAdminFacade;
    }
}
