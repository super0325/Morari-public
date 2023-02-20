package com.campingmapping.team4.spring.t424camp.model.dao.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campingmapping.team4.spring.t424camp.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value = "select * from camporder where fkuserid = :fkuserid", nativeQuery = true)
	Page<Order>findMyOrdersByUID(Pageable pageable, @Param("fkuserid") UUID fkuserid);

}
