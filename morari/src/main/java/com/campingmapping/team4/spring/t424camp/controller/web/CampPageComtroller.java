package com.campingmapping.team4.spring.t424camp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/camp")
public class CampPageComtroller {

	@GetMapping({ "", "/" })
	public String campIndex() {
		return "camp/guest/guestIndex";
	}

}
