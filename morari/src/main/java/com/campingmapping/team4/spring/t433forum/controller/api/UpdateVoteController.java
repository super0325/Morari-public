package com.campingmapping.team4.spring.t433forum.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.service.EmailService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteRecordService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteService;

@RestController
public class UpdateVoteController {

	@Autowired
	private VoteService voteService;
	@Autowired
	private VoteRecordService voteRecordService;
	@Autowired
	private EmailService emailService;

	// 隱藏投票
	@PutMapping("/hidevote.controller/{voteid}")
	public String hideVote(@PathVariable Integer voteid) {
		voteService.hideVote(voteid);
		return "true";
	}

	// 進行投票
	@PutMapping("/cancelhidevote.controller/{voteid}")
	public String cancelHideVote(@PathVariable Integer voteid) {
		voteService.cancelHideVote(voteid);
		return "true";
	}

	// 結束投票
	@PutMapping("/endvote.controller/{voteid}")
	public String endVote(@PathVariable Integer voteid) {
		voteService.endVote(voteid);
		return "true";
	}

	// 抽獎
	@PutMapping("/updatewinner.controller/{voteid}")
	public Boolean updateWinner(@PathVariable Integer voteid) {
		Vote vote = voteRecordService.updateWinner(voteid);
		if(vote != null) {
			String email = vote.getWinner().getEmail();
			String title = "morari恭喜你中獎";
			String message = "恭喜您在「" + vote.getVotename() + "」投票中中獎，獲得登山包一個！";
			emailService.sendEmail(email, title, message);
			return true;
		}
		return false;
	}
}
