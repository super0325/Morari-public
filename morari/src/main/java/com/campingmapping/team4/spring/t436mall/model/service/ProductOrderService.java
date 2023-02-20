package com.campingmapping.team4.spring.t436mall.model.service;

import java.util.List;

import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVoRequest;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrder;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderVo;

public interface ProductOrderService {

	// 根據購物車新增一筆訂單
	public ProductOrder create(List<ProductCartVoRequest> productcartvorequest,
			String odrecipient, String odrecipientphone,
			String odshippingaddress, Integer money);
	// 依orderID來搜尋單筆訂單
	public List<ProductOrderVo> selectById(String id);
	// 依userID來搜尋所有訂單
	public List<ProductOrder> selectAllByUserId(String userid);
	// 搜尋所有訂單(只有後臺能使用)
	public List<ProductOrder> selectAll();
	// 修改訂單狀態
	public void updateProductOrderSatusById(String odstatus,String orderId);
	// 修改訂單出貨地址、收件人、手機號(只有後臺能使用)
	public ProductOrder updateById(ProductOrder productorder);
	// 依userID來修改單筆產品(只有後臺能使用)
}
