package com.campingmapping.team4.spring.t436mall.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t436mall.model.dao.repository.ProductOrderDetailRepository;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetail;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetailVo;
import com.campingmapping.team4.spring.t436mall.model.service.ProductOrderDetailService;

@Service
@Transactional
public class ProductOrderDetailServiceImpl implements ProductOrderDetailService {

	@Autowired
	public ProductOrderDetailRepository pODDao;

	// 根據產品編號新增一筆訂單詳情
	@Override
	public ProductOrderDetail create(ProductOrderDetail productorderdetail) {
		return pODDao.save(productorderdetail);
	}
	// 搜尋所有訂單詳情
	@Override
	public List<ProductOrderDetail> selectAll() {
		return pODDao.findAll();
	}
	// 根據訂單編號搜尋所有訂單詳情
	@Override
	public List<ProductOrderDetailVo> selectAllByPdorderid(String pdorderid) {
		return pODDao.findByPdorderid(pdorderid);
	}
	
	
}
