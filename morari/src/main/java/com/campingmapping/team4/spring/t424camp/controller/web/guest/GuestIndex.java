package com.campingmapping.team4.spring.t424camp.controller.web.guest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.City;
import com.campingmapping.team4.spring.t424camp.model.entity.Tag;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.CityService;
import com.campingmapping.team4.spring.t424camp.model.service.TagService;

@Controller
@RequestMapping("/camp")
public class GuestIndex {
	
	@Autowired
	private CampService campService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TagService tagService;
	
	
	@GetMapping("/guestAllCamp/{page}")
	@ResponseBody
	public Object processAction(@PathVariable("page")@Nullable Integer page) {
		if(page == null) {
			page = 1;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		int pageSize = 3;
		Pageable pageable = PageRequest.of(page-1, pageSize);
		Page<Camp> pageList = campService.getByPage(pageable);
		
		List<Camp> campList = pageList.getContent();
		map.put("campList", campList);
		
		int totalPages = pageList.getTotalPages();
		map.put("totalPages", totalPages);
		
		long totalOrders = pageList.getTotalElements();
		map.put("totalOrders", totalOrders);
		
		List<Tag> tagList = tagService.findAll();
		List<City> cityList = cityService.findAll();

		map.put("tagList", tagList);
		map.put("cityList", cityList);

		return map;
	}

}
