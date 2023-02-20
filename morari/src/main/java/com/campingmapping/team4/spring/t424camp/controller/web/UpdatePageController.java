package com.campingmapping.team4.spring.t424camp.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.SiteService;

@Controller
@RequestMapping("/admin/camp")
public class UpdatePageController {
	
	@Autowired
	private CampService campService;
	
	@Autowired
	private SiteService siteService;
	
	
	@GetMapping("/UpdateCampByIDForm")
	public String toUpadteCampPage() {
		return "camp/admin/UpdateCampByIDForm" ;
	}

	@GetMapping("/UpdateSiteByIDForm")
	public String toUpadteSitePage() {
		return "camp/admin/UpdateSiteByIDForm" ;
	}
	
	@PostMapping("/upadteCampPage.controller")
	@ResponseBody
	public Camp upadteCampPage(@RequestBody int campID, Model m) {

		Camp camp = campService.findById(campID);

		m.addAttribute("camp", camp);

		return camp;
	}

	@PostMapping("/upadteSitePage.controller")
	@ResponseBody
	public Site upadteSitePage(@RequestBody int siteID, Model m) {

		Site site = siteService.findBySiteId(siteID);

		m.addAttribute("site", site);

		return site;
	}

}
