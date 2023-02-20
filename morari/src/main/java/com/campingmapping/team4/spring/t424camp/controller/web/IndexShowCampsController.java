package com.campingmapping.team4.spring.t424camp.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.City;
import com.campingmapping.team4.spring.t424camp.model.entity.Tag;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.CityService;
import com.campingmapping.team4.spring.t424camp.model.service.TagService;

@Controller
@RequestMapping("/admin/camp")
public class IndexShowCampsController {

	@Autowired
	private CampService campService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TagService tagService;
	
	
	@GetMapping("/campindex")
	public String showCampIndex() {
		return "camp/admin/AdminCampIndex";
	}

	@GetMapping("/indexShowAllCamp.controller")
	@ResponseBody
	public Object processAction(Model m) {
		List<Camp> allCamps = campService.findAll();
		List<Tag> tagList = tagService.findAll();
		List<City> cityList = cityService.findAll();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allCamps", allCamps);
		map.put("tagList", tagList);
		map.put("cityList", cityList);

		return map;
	}

}
