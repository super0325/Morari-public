package com.campingmapping.team4.spring.t424camp.controller.web.guest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.campingmapping.team4.spring.t424camp.model.entity.Order;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.OrderService;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/camp")
public class myOrders {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private CampService campService;
	
	
	@GetMapping("/myOrders")
	public String toMyOrderIndex() {
		return "camp/guest/myOrderIndex";
	}
	
	@GetMapping("/myOrders/{page}")
	@ResponseBody
	public Object showMyOrders(@PathVariable("page")@Nullable Integer page ) {
		if(page == null) {
			page = 1;
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//使用者
		UUID uid = jwtService.getUId(httpServletRequest);
		
		int pageSize = 3;
		Pageable pageable = PageRequest.of(page-1, pageSize);
		Page<Order> pageList = orderService.getByPageAndUID(pageable, uid);
		
		List<Order> orderList = pageList.getContent();
		map.put("orderList", orderList);
		
		int totalPages = pageList.getTotalPages();
		map.put("totalPages", totalPages);
		
		long totalOrders = pageList.getTotalElements();
		map.put("totalOrders", totalOrders);
		
		List<Camp> recommend = campService.recommendCampToUser(uid);
		map.put("recommend", recommend);
		
		return map;
	}

}
