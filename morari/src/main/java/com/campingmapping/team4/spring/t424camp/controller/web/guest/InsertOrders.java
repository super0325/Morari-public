package com.campingmapping.team4.spring.t424camp.controller.web.guest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Order;
import com.campingmapping.team4.spring.t424camp.model.entity.Orderitem;
import com.campingmapping.team4.spring.t424camp.model.service.OrderService;
import com.campingmapping.team4.spring.utils.service.JwtService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/camp")
public class InsertOrders {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	@GetMapping("/insertorders")
	public String toInsertOrders() {
		return "camp/guest/insertorders";
	}
	
	@PostMapping("/insertOrderByGuest")
	@ResponseBody
	public Object insertOrderByGuest (@RequestParam("siteIds")@Nullable Integer[] siteIds,
			@RequestParam("nums")@Nullable Integer[] nums,
			@RequestParam("goingdate")@Nullable String goingtimeString, 
			@RequestParam("leavingdate")@Nullable String leavingtimeString,
			@RequestParam("campID")@Nullable Integer campID
			) {
		
		// 存錯誤的map
		Map<String, String> errors = new HashMap<>();
		
		
		//營區
		if(siteIds == null || siteIds.length == 0) {
			errors.put("siteIds", "請選擇營區");
		}
		
		//數量
		if(Arrays.asList(nums).stream().filter(num -> num > 0).count() == 0) {
			errors.put("nums", "至少選擇其中一個營位的數量");
		}
		
		//出入營日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date goingtime = null;
		Date leavingtime = null;
		try {
			goingtime = sdf.parse(goingtimeString);
			leavingtime = sdf.parse(leavingtimeString);
			if(goingtime.after(leavingtime)) {
				errors.put("timeString", "入營時間 不得晚於 離營時間");
			}
			if(goingtime.equals(leavingtime)) {
				errors.put("timeString", "入營時間與離營時間 不得同一天");
			}
		} catch (ParseException e) {
			errors.put("timeString", "錯誤日期格式");
			e.printStackTrace();
		}
		
		//使用者
		UUID uid = jwtService.getUId(httpServletRequest);
		
		// 錯誤導回
		if (errors != null && !errors.isEmpty()) {
			errors.put("error", "true");
			return errors;
		}		
		
		
		Order order = orderService.insert(uid, siteIds, nums, goingtime, leavingtime, campID);
		
		//空值
		if(order == null) {
			errors.put("error", "none");
			errors.put("noData", "訂單已滿, 新增訂單失敗");
			return errors;
		}
		
		
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("successOrderByGuest", order);
		
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String nowString = dateFormat.format(now);
		
		Set<Orderitem> orderitems = order.getOrderitems();
		String items = "";
		for (Orderitem orderitem : orderitems) {
			items += orderitem.getSite().getSiteName() + "#";
		}
		
		
	    Random rand = new Random();
	    int randomNum = rand.nextInt(10000);
	    String randomNumString = String.format("%04d", randomNum);
		
		AllInOne all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("product" + randomNumString + + order.getOrderID());
		obj.setMerchantTradeDate(nowString);
		obj.setTotalAmount(order.getTotalPrice().toString());
		obj.setTradeDesc("test Description");
		obj.setItemName(items);
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setClientBackURL("https://localhost:8443/morari/camp/myOrders");
		obj.setNeedExtraPaidInfo("N");
		String form = all.aioCheckOut(obj, null);
		return form;

	}

	
}
