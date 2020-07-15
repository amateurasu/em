package com.viettel.ems.api;

import com.viettel.ems.model.OLT;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/olt")
public class OltApi {
    @PostMapping
    public String createOlt(OLT olt) {
        //TODO define this
        return "";
    }

    @GetMapping
    public List<OLT> getOltList() {
        //TODO define this
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public List<OLT> getOlt(@PathVariable long id) {
        //TODO define this
        return new ArrayList<>();
    }

    @DeleteMapping("/{id}")
    public void deleteOlt(@PathVariable long id) {
        //TODO define this
    }
}
