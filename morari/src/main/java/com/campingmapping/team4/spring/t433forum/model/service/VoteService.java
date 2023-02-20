package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t433forum.model.dao.repository.VoteRepository;
import com.campingmapping.team4.spring.t433forum.model.entity.Vote;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VoteService {
	@Autowired
	private VoteRepository voteRepository;
	
	// 查所有投票
	public List<Vote> showAllVote() {
		return voteRepository.findAll();
	}
	
	// 依voteid查vote
	public Vote showVote(Integer voteid){
		return voteRepository.findById(voteid).get();
	}
	
	// 查投票中的投票
	public List<Vote> showVoting(){
		return voteRepository.findByVoting(1);
	}
	
	// 查已結束的投票
	public List<Vote> showVoted(){
		return voteRepository.findByVoting(2);
	}
	
	// 新增投票 
	public Vote insertVote(Vote vote) {
		return voteRepository.save(vote);
	}
	
	// 隱藏投票
	public Vote hideVote(Integer voteid) {
		Vote vote = voteRepository.findById(voteid).get();
		vote.setVoting(0);
		return voteRepository.save(vote);
	}

	// 進行投票
	public Vote cancelHideVote(Integer voteid) {
		Vote vote = voteRepository.findById(voteid).get();
		vote.setVoting(1);
		return voteRepository.save(vote);
	}
	
	// 結束投票
	public Vote endVote(Integer voteid) {
		Vote vote = voteRepository.findById(voteid).get();
		vote.setVoting(2);
		return voteRepository.save(vote);
	}
}
