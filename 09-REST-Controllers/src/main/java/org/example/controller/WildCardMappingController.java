package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wildcard")
public class WildCardMappingController {

    //wildcard/test/*/hello
    @GetMapping("/test/*/hello")
    public String sayHello() {
        return "Cutiess";
    }

    @GetMapping("/hello/*/*")
    public String sayHello2() {
        return "Cutiess 2";
    }

}
