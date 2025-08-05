package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class ExactMappingController {

    //api = postman eke mehema denn one
    @GetMapping
    public String sayHello() {
        return "Hellowww";
    }

    // api/get/2
    @GetMapping("/get/2")
    public String sayHello2() {
        return "Hellowww 2";
    }

    // api/get/2/3
    @GetMapping("/get/2/3")
    public String sayHello3() {
        return "Hellowww 3";
    }

    // api/get/2/3/4
    @GetMapping("/get/2/3/4")
    public String sayHello4() {
        return "Hellowww 4";
    }

}
