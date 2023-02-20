package com.campingmapping.team4.spring.t401member.model.service;

public interface ToolService {
	public String loginsha1Hex(String password);

	public String remberloginsha1Hex(String key, String password);

	public String rembersha1Hex(String password);
}
