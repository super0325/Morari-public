package com.campingmapping.team4.spring.t401member.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/camper")
public class CamperAdminComtroller {

    @GetMapping("/alluser")
    public String camperIndex() {
        return "camper/admin/alluser";
    }
    @GetMapping("/guestedit")
    public String guestEdit() {
        return "camper/admin/guestedit";
    }

}
