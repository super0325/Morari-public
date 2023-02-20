package com.campingmapping.team4.spring.t424camp.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.CampRepository;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.OrderRepository;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.OrderitemRepository;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.SiteRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.Order;
import com.campingmapping.team4.spring.t424camp.model.entity.Orderitem;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;

@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderitemRepository orderitemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CampRepository campRepository;
	
	@Autowired
	private SiteRepository siteRepository;
	
	
	//更新訂單(狀態)
	public Order update(Order order) {
		return orderRepository.save(order);
	}
	
	//ID查詢訂單
	public Order findByOrderId(int orderId) {
		return orderRepository.findById(orderId).get();
	}
	
	//UID查詢訂單, 分頁, 倒序
	public Page<Order> getByPageAndUID(Pageable pageable, UUID fkuserid) {
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("orderID").descending());
		
		Page<Order> findMy = orderRepository.findMyOrdersByUID(sortedPageable, fkuserid);
		Date now = new Date();
		
		for (Order order : findMy) {
			Date leavingTime = order.getLeavingTime();
			if(leavingTime.before(now)) {
				order.setStatus("訂單已完成");
				orderRepository.save(order);
			}
		}
		return findMy;
	}
	
	//查詢全部訂單, 分頁, 倒序
	public Page<Order> getByPage(Pageable pageable) {
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("orderID").descending());
		Page<Order> findAll = orderRepository.findAll(sortedPageable);
		Date now = new Date();

		for (Order order : findAll) {
			Date leavingTime = order.getLeavingTime();
			if(leavingTime.before(now)) {
				order.setStatus("訂單已完成");
				orderRepository.save(order);
			}
		}
		return findAll;
	}


	
	//查詢全部訂單
	public List<Order> findAll() {
		List<Order> findAll = orderRepository.findAll();
		Date now = new Date();
		
		for (Order order : findAll) {
			Date leavingTime = order.getLeavingTime();
			if(leavingTime.before(now)) {
				order.setStatus("訂單已完成");
				orderRepository.save(order);
			}
		}
		return findAll;
	}
	
	//新增訂單
	public Order insert(UUID uid, Integer[] siteIds, Integer[] nums, Date goingtime, Date leavingtime, Integer campID) {
		
		UserProfiles user = userRepository.findById(uid).get();
		
		Camp camp = campRepository.findById(campID).get();
		
		Date now = new Date();

		Set<Orderitem> orderitems = new HashSet<Orderitem>();
		int totalPrice = 0;
		for (int i=0; i<nums.length; i++) {
			if(nums[i] > 0) {
				int siteId = siteIds[i];
				Site site = siteRepository.findById(siteId).get();
				if(!checkRest(site, nums[i], goingtime, leavingtime)) {
					return null;
				}
				Integer price = site.getSiteMoney();
				totalPrice += price * nums[i];
				
				orderitems.add(new Orderitem(site, goingtime, leavingtime, nums[i], price)) ;
			}
		}
		
		Order order = new Order(user, now, goingtime, leavingtime, "訂單付款成功", totalPrice, camp, orderitems);
		
		order =  orderRepository.save(order);
		
		for (Orderitem orderitem: orderitems) {
			orderitem.setCampOrder(order);
			orderitemRepository.save(orderitem);
		}
		
		return order;
	}
	
	
	//檢查當下是否有剩餘營位
	private boolean checkRest(Site site, Integer num, Date goingtime, Date leavingtime) {
//		int daysBetween = (int) TimeUnit.DAYS.convert(leavingtime.getTime() - goingtime.getTime(), TimeUnit.MILLISECONDS);		
		while(goingtime.before(leavingtime)) {
			List<Orderitem> items = orderitemRepository.findBySiteidAndLessThanGoingDate(site.getSiteID(), goingtime);
			long totalNums = items.stream().mapToInt(Orderitem::getNumbers).sum();
			if ((site.getTotalSites() - totalNums) < num) {
				return false;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(goingtime);
			cal.add(Calendar.DATE, 1);
			goingtime = cal.getTime();
		}
		
		return true;
	}
	
	//回傳60天內哪個日期是滿位
	public List<Integer> returnFulledDay(Site site) {
		Calendar cal = Calendar.getInstance();
		int counter = 0;
		
		Date go = new Date();
		
		List<Integer> fullDates = new ArrayList<Integer>();
		while(counter <= 60) {
			
			List<Orderitem> items = orderitemRepository.findBySiteidAndLessThanGoingDate(site.getSiteID(), go);
			long totalNums = items.stream().mapToInt(Orderitem::getNumbers).sum();
			if ((site.getTotalSites() - totalNums) <= 0) {
				fullDates.add(counter);
			}
			cal.setTime(go);
			cal.add(Calendar.DATE, 1);
			go = cal.getTime();
			
			counter++;
		}
		
		return fullDates;
	}
	
	
}
