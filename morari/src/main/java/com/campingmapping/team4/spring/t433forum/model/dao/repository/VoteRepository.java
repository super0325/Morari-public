package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t433forum.model.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

	// 依投票狀態查詢投票
	public List<Vote> findByVoting(int voting);
}
