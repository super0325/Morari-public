package com.campingmapping.team4.spring.t436mall.model.service;

import java.util.List;

import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetail;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetailVo;

public interface ProductOrderDetailService {

	// 根據產品編號新增一筆訂單詳情
	public ProductOrderDetail create(ProductOrderDetail productorderdetail);
	// 搜尋所有訂單詳情
	public List<ProductOrderDetail> selectAll();
	// 根據訂單編號搜尋所有訂單詳情
	public List<ProductOrderDetailVo> selectAllByPdorderid(String pdorderid);
}
