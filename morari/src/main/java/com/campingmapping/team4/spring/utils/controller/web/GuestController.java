package com.campingmapping.team4.spring.utils.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping({ "/", "" })
    public String wellcome() {
        return "wellcome";
    }

    @GetMapping("/home")
    public String index() {
        return "index";
    }
}