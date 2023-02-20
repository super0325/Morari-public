package com.campingmapping.team4.spring.utils.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class GetUidWebController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/utils/getuid")
    public String getUidWeb() {

        return jwtService.getStringUId(request);
    }

}