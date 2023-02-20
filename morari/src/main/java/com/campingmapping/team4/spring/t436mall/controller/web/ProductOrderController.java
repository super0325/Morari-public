package com.campingmapping.team4.spring.t436mall.controller.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVoRequest;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrder;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderVo;
import com.campingmapping.team4.spring.t436mall.model.service.impl.ProductOrderServiceImpl;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ProductOrder")
public class ProductOrderController {

	@Autowired
	JwtService jwtService;
	@Autowired
	private ProductOrderServiceImpl pOServiceImpl;

	@GetMapping("/orderPreview.controller/{id}")
	public String orderPreviewAction() {
		return "mall/guest/orderPreview";
	}

	@GetMapping("/AllorderAction.controller")
	public String AllorderAction() {
		return "mall/admin/orderindex";
	}

	@GetMapping("/selectorderbyuseridAction.controller")
	public String selectorderbyuseridAction() {
		return "mall/guest/selectorderbyuserid";
	}

	// 根據購物車新增一筆訂單
	@PostMapping("/create")
	@ResponseBody
	public String create(@RequestBody List<ProductCartVoRequest> productcartvorequest,
			@RequestParam("odrecipient") String odrecipient,
			@RequestParam("odrecipientphone") String odrecipientphone,
			@RequestParam("odshippingaddress") String odshippingaddress,
			@RequestParam("money") Integer money) {
		pOServiceImpl.create(productcartvorequest, odrecipient, odrecipientphone, odshippingaddress, money);
		return "結帳成功";
	}

	// 依orderID來搜尋單筆訂單
	@GetMapping("/selectById/{id}")
	@ResponseBody
	public List<ProductOrderVo> selectById(@PathVariable String id) {
		return pOServiceImpl.selectById(id);
	}

	// 依userID來搜尋所有訂單
	@GetMapping("/selectAllByUserId")
	@ResponseBody
	public List<ProductOrder> selectAllByUserId(HttpServletRequest request) {
		UUID uid = jwtService.getUId(request);
		return pOServiceImpl.selectAllByUserId(uid.toString());
	}

	// 搜尋所有訂單(只有後臺能使用)
	@GetMapping("/selectAll")
	@ResponseBody
	public List<ProductOrder> selectAll() {
		return pOServiceImpl.selectAll();
	}

	// 修改訂單狀態
	@PutMapping("/updateProductOrderSatusById")
	@ResponseBody
	public void updateProductOrderSatusById(@RequestParam String odstatus, @RequestParam String orderId) {
		pOServiceImpl.updateProductOrderSatusById(odstatus, orderId);
	}

	// 修改訂單出貨地址、收件人、手機號(只有後臺能使用)
	@PutMapping("/updateById")
	@ResponseBody
	public ProductOrder updateById(@PathVariable ProductOrder productorder) {
		return pOServiceImpl.updateById(productorder);
	}
	// 依userID來修改單筆產品(只有後臺能使用)

}
