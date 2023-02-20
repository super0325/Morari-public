package com.campingmapping.team4.spring.t436mall.model.service;

import java.util.List;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCart;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVo;

public interface ProductCartService {

	// 依userid新增一筆購物車資料
	public ProductCart create(ProductCart productcart);
	// 依cartid來增加or減少購物車產品數量
	public ProductCart updataById(ProductCart productcart);
	// 依cartid刪除購物車
	public void deleteById(Integer id);
	// 依userid清空購物車、或結帳
	public void deleteAllByUserId(String userid);
	// 依userID查詢購物車資料
	public List<ProductCartVo> selectAllByUserId(String userid);
	// 查詢購物車所有資料(後台)
	public List<ProductCartVo> selectAllVo();
	// 查詢產品是否已在用戶購物車內(前台)
	public ProductCart findByUseridPdid(String userid,Integer pdid);
}
