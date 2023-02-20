package com.campingmapping.team4.spring.t424camp.controller.web.siteCrud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.service.SiteService;

@Controller
@RequestMapping("/admin/camp")
public class DeleteSiteByIDController {

	@Autowired
	private SiteService siteService;

	@PostMapping("/deleteSiteByID.controller")
	@ResponseBody
	public boolean deleteSiteByID(@RequestBody int siteID, Model m) {
		siteService.deleteById(siteID);

//		m.addAttribute("ID", "siteID: " + Integer.toString(siteID) + " 刪除成功");

		return true;
	}

}
