package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteRecord;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {

	// 依option及user查喜歡或不喜歡
	public VoteRecord findByVoteoptionAndUserprofiles(VoteOption voteOption, UserProfiles userprofiles);
	
	// 依option查record
	public List<VoteRecord> findByVoteoption(VoteOption voteOption);
}
