package com.campingmapping.team4.spring.t424camp.controller.web.guest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.OrderService;
import com.campingmapping.team4.spring.t424camp.model.service.SiteService;

@Controller
@RequestMapping("/camp")
public class ReturnFulledDays {
	
	@Autowired
	private CampService campService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/fullCalendar")
	public String toFullCalendar() {
		return "camp/guest/FullCalendar" ;
	}
	
	@GetMapping("/returnFulledDay/{campID}")
	@ResponseBody
	public List<Integer> returnFulledDay(@PathVariable("campID") String campID) {
		Camp camp = campService.findById(Integer.valueOf(campID));
		Set<Site> sites = camp.getSites();
		int size = sites.size();
		
		List<Integer> datelist = new ArrayList<Integer>();
		
		for (Site site : sites) {
			Site findSite = siteService.findBySiteId(Integer.valueOf(site.getSiteID()));
			datelist.addAll(orderService.returnFulledDay(findSite));
		}
		
		Map<Integer, Integer> map = new HashMap<>();
		for (Integer date : datelist) {
		  if (map.containsKey(date)) {
		    map.put(date, map.get(date) + 1);
		  } else {
		    map.put(date, 1);
		  }
		}

		List<Integer> resultlist = new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
		  if (entry.getValue() == size) {
		    // 找到次數為 size 的某一物件
		    resultlist.add(entry.getKey());
		  }
		}

		return resultlist;
	}

}
