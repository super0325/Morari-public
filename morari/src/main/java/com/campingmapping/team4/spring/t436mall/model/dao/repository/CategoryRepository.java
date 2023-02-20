package com.campingmapping.team4.spring.t436mall.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campingmapping.team4.spring.t436mall.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


	// 新增一筆產品
	// 依Pdid來刪除單筆產品
	// 依Pdid來修改單筆產品
	// 依Pdid來搜尋單筆產品
	// 搜尋所有產品
	// 購買產品修改庫存
	// 依pdtype來搜尋所有產品
	@Query(value = "select * from category where pdtype= ?1", nativeQuery = true)
	public List<Category> findByType(String pdtype);
	@Query(value = "select * from category where userid= ?1", nativeQuery = true)
	public List<Category> findByUserID(String userid);
}
