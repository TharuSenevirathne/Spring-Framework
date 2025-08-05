package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("char")
public class CharacterMappingController {

    // char/item/Iyug = worked
    // char/item/I123 = worked
    // char/item/I@@@ = worked
    // char/item/i123 = not worked
    // char/item/Iyugf = not worked
    @GetMapping("item/I???") // meke I ge pitipassata monawa hri letters 3k enna one req eka send wenn
    public String index() {
        return "Character Mapping Controller 1";
    }

    // char/asdf/search = worked
    // char/1234/search = worked
    // char/@@@@/search = worked
    // char/123456/search = not worked
    // char/####/search = not worked
    @GetMapping("????/search")
    public String index2() {
        return "Character Mapping Controller 2";
    }

}
