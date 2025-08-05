package org.example.controller;

import org.example.dto.CityDTO;
import org.example.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json2")
public class JSON2Controller {

    //Terminal eke print krgnnw POSTMAN eken dila
    @PostMapping("save")
    public String saveUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getName());
        System.out.println(userDTO.getPassword());
        System.out.println(userDTO.getAddress());
        System.out.println(userDTO.getCity().getName());
        System.out.println(userDTO.getCity().getCode());
        return "User Saved Successfully";
    }

    // Postman eke print krgnnw methanin values return krl
    @GetMapping("get")
    public UserDTO getUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Saman");
        userDTO.setPassword("12");
        userDTO.setAddress("Colombo");

        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Maradana");
        cityDTO.setCode("101");

        userDTO.setCity(cityDTO);
        return userDTO;
    }

}
