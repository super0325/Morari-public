package com.campingmapping.team4.spring.t401member.model.service;

import java.util.Map;

public interface JoinService {
	public Map<String, Object> accountsame(String account);

	public int joinNewMember(String account, String password, String email,
			String birthday);
}
