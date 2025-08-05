package org.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    // Handler Mappings

    @GetMapping
    public String getMapping() {
        return "GET";
    }

    @GetMapping(path = "/get2")
    public String getMapping2() {
        return "GET-2";
    }

    @PostMapping
    public String postMapping() {
        return "POST";
    }

    @PutMapping
    public String putMapping() {
        return "PUT";
    }

    @DeleteMapping
    public String deleteMapping() {
        return "DELETE";
    }

    @PatchMapping // adala kotasa witharak update krnn ganne PATCH eka
    public String patchMapping() {
        return "PATCH";
    }
}
