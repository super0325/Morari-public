package com.campingmapping.team4.spring.t436mall.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t436mall.model.dao.repository.ProductOrderDetailRepository;
import com.campingmapping.team4.spring.t436mall.model.dao.repository.ProductOrderRepository;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVoRequest;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrder;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetail;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderVo;
import com.campingmapping.team4.spring.t436mall.model.service.ProductOrderService;

@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

	@Autowired
	public ProductOrderRepository pDao;

	@Autowired
	public ProductOrderDetailRepository pODDao;

	@Autowired
	public ProductOrderDetailServiceImpl pODServ;

	@Autowired
	public ProductCartServiceImpl pCarServ;

	// 根據購物車新增一筆訂單
	@Override
	public ProductOrder create(List<ProductCartVoRequest> productcartvorequest,
			String odrecipient, String odrecipientphone,
			String odshippingaddress, Integer money) {
		ProductOrder order = new ProductOrder();
		Date now = new Date();
		order.setDatetime(now);
		order.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		order.setOdstatus("未付款");
		order.setUserid(productcartvorequest.get(0).getUserid());
		order.setOdrecipient(odrecipient);
		order.setOdrecipientphone(odrecipientphone);
		order.setOdshippingaddress(odshippingaddress);
		order.setMoney(money);
		pDao.save(order);

		for (ProductCartVoRequest pVoRequest : productcartvorequest) {
			ProductOrderDetail pODetail = new ProductOrderDetail();
			pODetail.setPdid(pVoRequest.getPdid());
			pODetail.setPdqty(pVoRequest.getCtqty());
			pODetail.setPdorderid(order.getId());
			pODServ.create(pODetail);
		}
		pCarServ.deleteAllByUserId(order.getUserid());
		return order;
	}
	// 依orderID來搜尋單筆訂單
	@Override
	public List<ProductOrderVo> selectById(String id) {
		return pDao.findById(id);
	}
	// 依userID來搜尋所有訂單
	@Override
	public List<ProductOrder> selectAllByUserId(String userid) {

		return pDao.findByUserid(userid);
	}
	// 搜尋所有訂單(只有後臺能使用)
	@Override
	public List<ProductOrder> selectAll() {
		List<ProductOrder> orders = pDao.findAll();
		Collections.sort(orders, new Comparator<ProductOrder>() {
			@Override
			public int compare(ProductOrder order1, ProductOrder order2) {
				return order2.getDatetime().compareTo(order1.getDatetime());
			}
		});
		return orders;
	}
	// 修改訂單狀態
	@Override
	public void updateProductOrderSatusById(String odstatus,String orderId) {
		pDao.updateProductOrderSatusById(odstatus, orderId);
	}
	// 修改訂單出貨地址、收件人、手機號(只有後臺能使用)
	@Override
	public ProductOrder updateById(ProductOrder productorder) {
		return pDao.save(productorder);
	}
	// 依userID來修改單筆產品(只有後臺能使用)
}
