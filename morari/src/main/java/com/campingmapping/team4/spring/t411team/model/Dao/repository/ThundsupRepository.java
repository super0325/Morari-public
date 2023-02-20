package com.campingmapping.team4.spring.t411team.model.Dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t411team.model.entity.Thundsup;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import java.util.List;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;

public interface ThundsupRepository extends JpaRepository<Thundsup, Integer> {

	List<Thundsup> findByUserprofiles(UserProfiles uid);
	
	List<Thundsup> findByInitiating(Initiating i);
	
	List<Thundsup> findByInitiatingAndUserprofiles(Initiating i, UserProfiles userProfiles);
}
