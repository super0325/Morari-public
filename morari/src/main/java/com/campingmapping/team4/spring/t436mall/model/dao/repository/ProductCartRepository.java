package com.campingmapping.team4.spring.t436mall.model.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.campingmapping.team4.spring.t436mall.model.entity.ProductCart;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductCartVo;

public interface ProductCartRepository extends JpaRepository<ProductCart, Integer> {

	// 依userid新增一筆購物車資料
	// 依cartid來增加or減少購物車產品數量
	// 刪除購物車
	// 依userid清空購物車
	@Modifying
	@Query(value = "delete from productcart where userid= ?1", nativeQuery = true)
	public void deleteAllByUserid(String userid);
	// 結帳->新增訂單
	// 依userID查詢購物車資料
	@Query(value = "SELECT p.id, p.userid, p.pdid, p.ctqty, c.pdpicture, c.pdprice, c.pdname,c.pdinventory"
			+ " FROM productcart p"
			+ " LEFT JOIN category c ON  p.pdid = c.pdid WHERE p.userid= ?1", nativeQuery = true)
	public List<ProductCartVo> findByUserid(String userid);
	// 查詢購物車所有資料(後台)
	@Query(value = "SELECT p.id, p.userid, p.pdid, p.ctqty, c.pdpicture, c.pdprice, c.pdname"
			+ " FROM productcart p"
			+ " LEFT JOIN category c ON  p.pdid = c.pdid", nativeQuery = true)
	public List<ProductCartVo> findAllVo();
	
	
	@Query(value = "SELECT * FROM productcart WHERE userid= ?1 and pdid= ?2", nativeQuery = true)
	public ProductCart findByUseridPdid(String userid,Integer pdid);
	
	

}
