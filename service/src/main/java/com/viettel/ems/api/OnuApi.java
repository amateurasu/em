package com.viettel.ems.api;

import com.viettel.ems.model.ONU;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onu")
public class OnuApi {

    @PostMapping
    public void createOnu(ONU onu) {

    }

    @DeleteMapping
    public void deleteOnu(ONU onu) {

    }
}
