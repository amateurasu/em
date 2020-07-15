package com.viettel.ems.api;

import com.viettel.ems.model.Splitter;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/splitter")
public class SplitterApi {
    @PostMapping
    public void createSplitter(@RequestBody Splitter splitter) {

    }

    @DeleteMapping
    public void deleteSplitter(@RequestBody Splitter splitter) {

    }
}
