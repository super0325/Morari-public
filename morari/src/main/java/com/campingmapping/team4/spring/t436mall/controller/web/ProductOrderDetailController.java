package com.campingmapping.team4.spring.t436mall.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetail;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetailVo;
import com.campingmapping.team4.spring.t436mall.model.service.impl.ProductOrderDetailServiceImpl;

@Controller
@RequestMapping("/ProductOrderDetail")
public class ProductOrderDetailController {

	@Autowired
	private ProductOrderDetailServiceImpl pODServiceImpl;
	
	@GetMapping("/selectAllByPdorderid.controller/{id}")
	public String selectAllByPdorderidAllAction() {
		return "mall/admin/selectOrderdetailById";
	}

	@GetMapping("/selectorderdetailbyuserid.controller/{id}")
	public String selectorderdetailbyuseridAllAction() {
		return "mall/guest/selectorderdetailbyuserid";
	}

	// 搜尋所有訂單詳情
	@GetMapping("/selectAll")
	@ResponseBody
	public List<ProductOrderDetail> selectAll() {
		return pODServiceImpl.selectAll();
	}
	
	// 根據訂單編號搜尋所有訂單詳情
	@GetMapping("/selectAllByPdorderid/{pdorderid}")
	@ResponseBody
	public List<ProductOrderDetailVo> selectAllByPdorderid(
			@PathVariable String pdorderid) {
		return pODServiceImpl.selectAllByPdorderid(pdorderid);
	}
	
}
