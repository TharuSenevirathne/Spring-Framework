package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("path")
public class PathVariableController {

    //path/senez
    @GetMapping(path = "{name}")
    public String hi(@PathVariable("name") String names) {
        return names;

        //@GetMapping(path = "{name}") & @PathVariable("name") meke samana wenna one name kiyn word eka
    }

    //path/senez/12
    @GetMapping(path = "{name}/{id}")
    public String hui(@PathVariable("name") String names, @PathVariable("id") int id) {
        return "Name: " + names +" "+ " ID: " + id;
    }

    //path/customer/I001
    @GetMapping(path = "customer/{id:[I][0-9]{3}}")
    public String hellow(@PathVariable("id") String id) {
        return "ID: " + id;
    }

    //path/item/I002/branch/panadura
    @GetMapping(path = "item/{code}/branch/{city}")
    public String hellow2(@PathVariable("code") String code, @PathVariable("city") String city) {
        return "Code: " + code +" "+ " City: " + city;
    }

    //path/item/I002/branch/panadura
    @GetMapping(path = "employee/{code:[E][0-9]{3}}/branch/{city}")
    public String hey(@PathVariable("code") String code, @PathVariable("city") String city) {
        return "Code: " + code +" "+ " City: " + city;
    }

}
