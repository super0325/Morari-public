package com.campingmapping.team4.spring.t433forum.model.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteAndOption {

	private Integer voteid;
	private String votename;
	private Integer voting;
	private String email;
	private Map<Integer , String> voteoption;
}
