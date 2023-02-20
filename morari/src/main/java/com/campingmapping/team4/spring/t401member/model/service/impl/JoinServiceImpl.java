package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t401member.model.service.JoinService;

@Service("JoinService")
public class JoinServiceImpl implements JoinService {

	@Override
	public Map<String, Object> accountsame(String account) {
		return null;
	}

	@Override
	public int joinNewMember(String account, String password, String email,
			String birthday) {
		return 0;
	}

}
