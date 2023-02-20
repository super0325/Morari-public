package com.campingmapping.team4.spring.t401member.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camper")
public class CamperPageComtroller {

    @GetMapping("/notlogin")
    public String camperIndex() {
        return "camper/guest/notlogin";
    }
    @GetMapping("/{uid}")
    public String camperDetail() {
        return "camper/guest/camperDetail";
    }

}
