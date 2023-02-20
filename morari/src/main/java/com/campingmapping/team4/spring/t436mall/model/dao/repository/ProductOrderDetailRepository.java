package com.campingmapping.team4.spring.t436mall.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetail;
import com.campingmapping.team4.spring.t436mall.model.entity.ProductOrderDetailVo;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail, Integer> {

	// 根據產品編號新增一筆訂單詳情
	// 搜尋所有訂單詳情
	// 根據訂單編號搜尋所有訂單詳情
	@Query(value = "SELECT p.pdorderid, p.pdid, p.pdqty, c.pdpicture, c.pdprice, c.pdname"
			+ " FROM productorderdetail p"
			+ " LEFT JOIN category c ON  p.pdid = c.pdid WHERE p.pdorderid= ?1", nativeQuery = true)
	public List<ProductOrderDetailVo> findByPdorderid(String pdorderid);

}
