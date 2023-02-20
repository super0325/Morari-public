package com.campingmapping.team4.spring.t433forum.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;
import com.campingmapping.team4.spring.t433forum.model.service.VoteOptionService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteRecordService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteService;

@RestController
public class InsertVoteController {
	@Autowired
	private VoteService voteService;
	@Autowired
	private VoteOptionService voteOptionService;
	@Autowired
	private VoteRecordService voteRecordService;
	
	// 新增投票
	@PostMapping("/insertvote.controller")
	public Vote processInsertVote(@RequestBody Vote vote) {
		return voteService.insertVote(vote);
	}
	
	// 新增投票選項
	@PostMapping("/insertvoteoption.controller")
	public String processInsertVoteOption(@RequestBody VoteOption voteOption) {
		voteOptionService.insertVoteOption(voteOption);
		return "true";
	}
	
	// 新增投票紀錄
	@PostMapping("insertvoterecord.controller/{optionid}")
	public String processInsertVoteRecord(@PathVariable int optionid) {
		Boolean insertVoteRecord = voteRecordService.insertVoteRecord(optionid);
		if(insertVoteRecord) {
			return "true";
		}
		return "false";
	}
}
