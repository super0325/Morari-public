package com.campingmapping.team4.spring.t411team.model.Dao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;
import java.lang.Integer;

public interface InitiatingRepository extends JpaRepository<Initiating, Integer> {
	
	public List<Initiating> findByStartdateGreaterThanEqual(Date startdate);
	
	public List<Initiating> findByEnddateLessThanEqual(Date enddate);
	
	public List<Initiating> findByStartdateGreaterThanEqualAndEnddateLessThanEqual(Date startdate, Date enddate);
	
	public List<Initiating> findByCamparea(String camparea);
	
	public List<Initiating> findByUserprofiles(UserProfiles uid);
	
}
