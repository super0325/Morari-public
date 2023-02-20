package com.campingmapping.team4.spring.t424camp.model.dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campingmapping.team4.spring.t424camp.model.entity.Orderitem;

public interface OrderitemRepository extends JpaRepository<Orderitem, Integer> {
	
	@Query(value = "select * from camporderitem where fksiteid = :fksiteid and goingtime <= :goingtime and leavingtime > :goingtime", nativeQuery = true)
	  List<Orderitem> findBySiteidAndLessThanGoingDate(@Param("fksiteid") int fksiteid,
	                                 @Param("goingtime") Date goingtime);

}
