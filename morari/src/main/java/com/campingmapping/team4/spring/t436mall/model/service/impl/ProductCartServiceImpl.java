package com.campingmapping.team4.spring.t436mall.model.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t436mall.model.dao.repository.ProductCartRepository;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCart;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVo;
import com.campingmapping.team4.spring.t436mall.model.service.ProductCartService;

@Service
@Transactional
public class ProductCartServiceImpl implements ProductCartService {

	@Autowired
	public ProductCartRepository pcDao;

	// 依userid新增一筆購物車資料
	@Override
	public ProductCart create(ProductCart productcart) {
		ProductCart original = pcDao.findByUseridPdid(productcart.getUserid(),
				productcart.getPdid());
		if (original == null) {

			productcart.setCtqty(1);
			return pcDao.save(productcart);
		}
		original.setCtqty(original.getCtqty() + 1);
		return pcDao.save(original);

	}
	// 依cartid來增加or減少購物車產品數量
	@Override
	public ProductCart updataById(ProductCart productcart) {
		ProductCart original = pcDao.findById(productcart.getId()).get();
		productcart.setPdid(original.getPdid());
		productcart.setUserid(original.getUserid());
		return pcDao.save(productcart);

	}
	// 依id刪除購物車
	@Override
	public void deleteById(Integer id) {
		pcDao.deleteById(id);
	}
	// 依userid清空購物車、或結帳
	@Override
	public void deleteAllByUserId(String userid) {
		pcDao.deleteAllByUserid(userid);
	}
	// 依userID查詢購物車資料
	@Override
	public List<ProductCartVo> selectAllByUserId(String userid) {
		return pcDao.findByUserid(userid);
	}
	// 查詢購物車所有資料(後台)
	@Override
	public List<ProductCartVo> selectAllVo() {
		return pcDao.findAllVo();
	}
	// 查詢產品是否已在用戶購物車內(前台)
	@Override
	public ProductCart findByUseridPdid(String userid, Integer pdid) {
		return pcDao.findByUseridPdid(userid, pdid);
	}

}
