package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.VoteOptionRepository;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.VoteRecordRepository;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.VoteRepository;
import com.campingmapping.team4.spring.t433forum.model.entity.Vote;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteOption;
import com.campingmapping.team4.spring.t433forum.model.entity.VoteRecord;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VoteRecordService {
	@Autowired
	private VoteRecordRepository voteRecordRepository;
	@Autowired
	private VoteOptionRepository voteOptionRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private HttpServletRequest request;

	// 新增投票紀錄
	public Boolean insertVoteRecord(int optionid) {
		UUID uid = jwtService.getUId(request);
		UserProfiles userProfiles = new UserProfiles();
		userProfiles.setUid(uid);

		Boolean showVoteRecord = showVoteRecord(optionid, userProfiles);
		if (showVoteRecord) {
			VoteRecord voteRecord = new VoteRecord();
			voteRecord.setUserprofiles(userProfiles);
			voteRecord.setVoteoption(voteOptionRepository.findById(optionid).get());
			voteRecordRepository.save(voteRecord);
			return true;
		}
		return false;
	}

	// 依option及user查投票紀錄
	public Boolean showVoteRecord(int optionid, UserProfiles userProfiles) {
		Vote vote = voteOptionRepository.findById(optionid).get().getVote();
		List<VoteOption> findByVote = voteOptionRepository.findByVote(vote);
		for (VoteOption option : findByVote) {
			VoteRecord findByVoteoptionAndUserprofiles = voteRecordRepository.findByVoteoptionAndUserprofiles(option,
					userProfiles);
			if (findByVoteoptionAndUserprofiles != null) {
				return false;
			}
		}
		return true;
	}

	// 依option查得票數
	public Integer showVoteRecord(VoteOption voteOption) {
		return voteRecordRepository.findByVoteoption(voteOption).size();
	}

	// 抽獎並記錄得獎者
	public Vote updateWinner(@PathVariable Integer voteid) {
		Vote vote = voteRepository.findById(voteid).get();
		if (vote.getWinner() == null) {
			Map<Integer, UserProfiles> map = new HashMap<>();
			int i = 0;
			List<VoteOption> voteOptions = voteOptionRepository.findByVote(vote);
			for (VoteOption option : voteOptions) {
				List<VoteRecord> voteRecord = voteRecordRepository.findByVoteoption(option);
				for (VoteRecord record : voteRecord) {
					UserProfiles user = record.getUserprofiles();
					if(user != null){
						map.put(i, user);
						i++;
					}
				}
			}
			if (i == 0) {
				return null;
			}
			int random = (int) (Math.random() * i);
			UserProfiles winner = map.get(random);
			vote.setWinner(winner);
			voteRepository.save(vote);
			return vote;
		}
		return null;
	}
}
