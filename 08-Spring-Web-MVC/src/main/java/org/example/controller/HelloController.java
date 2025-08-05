package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController // meken ui return krnn bh,REST API hdnn thamai use krnne
@Controller // meken ui return krnw
@RequestMapping("/hello")
public class HelloController {

    public HelloController() {
        System.out.println("Hello Controller");
    }

    @GetMapping("/index")
    public String hello2() {
        return "index";
    }

    @GetMapping("/item")
    public String item() {
        return "item";
    }
}
