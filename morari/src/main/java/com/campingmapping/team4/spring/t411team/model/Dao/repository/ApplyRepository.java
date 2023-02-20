package com.campingmapping.team4.spring.t411team.model.Dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t411team.model.entity.Apply;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;
import java.util.List;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import java.lang.Integer;

public interface ApplyRepository extends JpaRepository<Apply, Integer> {
	
	List<Apply> findByInitiatingAndResponsestate(Initiating initiating, int state);
	
	List<Apply> findByUserprofilesAndResponsestate(UserProfiles userprofiles, int state);
	
	List<Apply> findByInitiatingAndUserprofiles(Initiating initiating, UserProfiles userprofiles);
	
}
