package com.campingmapping.team4.spring.t401member.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageComtroller {

    @GetMapping({ "/login", "/login/" })
    public String login() {
        return "camper/global/login";
    }

    @GetMapping({ "/register", "/register/" })
    public String register() {
        return "camper/global/register";
    }

    @GetMapping({ "/forgotpassword", "/forgotpassword/" })
    public String forgotPassword() {
        return "camper/global/forgotpassword";
    }

}
