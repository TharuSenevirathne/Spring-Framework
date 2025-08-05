package org.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("param")
public class RequestParamController {

    @GetMapping(params = {"id"})
    public String getCustomer(@RequestParam("id") String id) {
        return "Query Param ID: " + id;
    }

    @GetMapping(params = {"id","name"})
    public String getCustomer2(@RequestParam("id") String id ,@RequestParam("name") String name) {
        return "Query Param ID: " + id +" "+ " Name: " + name;
    }

    @GetMapping(path = "path/{code}",params = {"id","name"})
    public String getCustomer3(@PathVariable("code") String code, @RequestParam("id") String id , @RequestParam("name") String name) {
        return "Query Param ID: " + id + " " + " Name: " + name + " " + " Code: " + code;
    }

}
