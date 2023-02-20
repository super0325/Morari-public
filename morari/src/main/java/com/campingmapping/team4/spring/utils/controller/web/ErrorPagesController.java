package com.campingmapping.team4.spring.utils.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ErrorPagesController {
    
    @GetMapping("/locked")
    public String locked(){
        return "error/locked";
    }
}