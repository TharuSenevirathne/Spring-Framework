package org.example.controller;

import org.example.dto.UserDTO;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/form")
public class FormURLDataController {

    //    @PostMapping("save")
//    public String saveUser(@RequestParam Map<String,String> formData){
//        for (Map.Entry<String, String> entry : formData.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//        return "User Saved Successfully";
//    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserDTO userDTO) {
        System.out.println(userDTO);
        return "User Saved Successfully";
    }
}
