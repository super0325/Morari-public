package com.campingmapping.team4.spring.t433forum.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowPostComment {
	
	private Integer postcommentid;
	private Integer postid;
	private UUID uid;
	private String postcomment;
	private Integer postcommentreport;
	private UUID informantuid;
	private Integer postcommenthide;
}
