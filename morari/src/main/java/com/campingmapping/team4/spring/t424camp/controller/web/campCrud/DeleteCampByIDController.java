package com.campingmapping.team4.spring.t424camp.controller.web.campCrud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.service.CampService;

@Controller
@RequestMapping("/admin/camp")
public class DeleteCampByIDController {

	@Autowired
	private CampService campService;

	@PostMapping("/deleteCampByID.controller")
	@ResponseBody
	public boolean deleteCampByID(@RequestBody int campID, Model m) {
		campService.deleteById(campID);
		return true;
	}

}
