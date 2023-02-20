package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;

public interface VoteOptionRepository extends JpaRepository<VoteOption, Integer> {

	// 依vote查voteOption
	public List<VoteOption> findByVote(Vote vote);
}
