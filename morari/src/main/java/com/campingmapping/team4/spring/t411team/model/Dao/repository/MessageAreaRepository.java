package com.campingmapping.team4.spring.t411team.model.Dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t411team.model.entity.MessageArea;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;
import java.util.List;

public interface MessageAreaRepository extends JpaRepository<MessageArea, Integer> {
	
	List<MessageArea> findByInitiating(Initiating initiating);
	
}
