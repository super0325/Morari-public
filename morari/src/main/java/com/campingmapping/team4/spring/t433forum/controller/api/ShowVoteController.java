package com.campingmapping.team4.spring.t433forum.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.t433forum.model.dto.OptionAndRecord;
import com.campingmapping.team4.spring.t433forum.model.dto.VoteAndOption;
import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;
import com.campingmapping.team4.spring.t433forum.model.service.VoteOptionService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteRecordService;
import com.campingmapping.team4.spring.t433forum.model.service.VoteService;

@RestController
public class ShowVoteController {
	@Autowired
	private VoteService voteService;
	@Autowired
	private VoteOptionService voteOptionService;
	@Autowired
	private VoteRecordService voteRecordService;
	
	// 查所有vote
	@GetMapping("/showallvote.controller")
	public List<Vote> processShowAllVote(){
		return voteService.showAllVote();
	}
	
	// 查投票中的投票
	@GetMapping("/showvoting.controller")
	public List<Vote> processShowVoting(){
		return voteService.showVoting();
	}
	
	// 查已結束的投票
	@GetMapping("/showvoted.controller")
	public List<Vote> processShowVoted(){
		return voteService.showVoted();
	}
	
	// 依vote查voteOption
	@GetMapping("/showvoteoptionbyvoteid.controller/{vote}")
	public List<VoteOption> processShowVoteOptionByVoteId(@PathVariable Vote vote){
		return voteOptionService.showVoteOptionByVoteId(vote);
	}
	
	// 依vote查選項名稱及投票人數
	@GetMapping("/showvoteoptionandvoterecordbyvoteid.controller/{voteid}")
	public List<OptionAndRecord> processShowVoteOptionAndVoteRecordByVoteId(@PathVariable Integer voteid){
		Vote vote = voteService.showVote(voteid);
		List<VoteOption> showVoteOptionByVoteId = voteOptionService.showVoteOptionByVoteId(vote);
		List<OptionAndRecord> list = new ArrayList<>();
		for(VoteOption voteOption : showVoteOptionByVoteId) {
			Integer showVoteRecord = voteRecordService.showVoteRecord(voteOption);
			OptionAndRecord optionAndRecord = new OptionAndRecord();
			optionAndRecord.setOptionid(voteOption.getOptionid());
			optionAndRecord.setOptionname(voteOption.getOptionname());
			optionAndRecord.setRecordcount(showVoteRecord);
			list.add(optionAndRecord);
		}
		return list;
	}
	
	// 查所有vote及option
	@GetMapping("/showvoteandvoteoption.controller")
	public List<VoteAndOption> processShowVoteAndOption() {
		List<VoteAndOption> list = new ArrayList<>();
		List<Vote> showAllVote = voteService.showAllVote();
		for(Vote vote : showAllVote) {
			VoteAndOption voteAndOption = new VoteAndOption();
			List<VoteOption> showVoteOptionByVoteId = voteOptionService.showVoteOptionByVoteId(vote);
			voteAndOption.setVoteid(vote.getVoteid());
			voteAndOption.setVotename(vote.getVotename());
			voteAndOption.setVoting(vote.getVoting());
			if(vote.getWinner() != null) {
				voteAndOption.setEmail(vote.getWinner().getEmail());
			}
			Map<Integer, String> map = new HashMap<>();
			int i = 1;
			for(VoteOption option : showVoteOptionByVoteId) {
				map.put(i, option.getOptionname());
				voteAndOption.setVoteoption(map);
				i++;
			}
			list.add(voteAndOption);
		}
		return list;
	}
	
	// 查最大voteOption
	@GetMapping("/showmaxoption.controller")
	public int processShowMaxOption() {
		List<Vote> showAllVote = voteService.showAllVote();
		int maxOption = 0;
		for(Vote vote : showAllVote) {
			List<VoteOption> showVoteOptionByVoteId = voteOptionService.showVoteOptionByVoteId(vote);
			if(maxOption < showVoteOptionByVoteId.size()) {
				maxOption = showVoteOptionByVoteId.size();
			}
		}
		return maxOption;
	}
	
}
