package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t433forum.model.dao.repository.VoteOptionRepository;
import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VoteOptionService {

	@Autowired
	private VoteOptionRepository voteOptionRepository;
	
	// 依vote查option
	public List<VoteOption> showVoteOptionByVoteId(Vote vote){
		return voteOptionRepository.findByVote(vote);
	}
	
	// 新增option
	public void insertVoteOption(VoteOption voteOption) {
		voteOptionRepository.save(voteOption);
	}
}
