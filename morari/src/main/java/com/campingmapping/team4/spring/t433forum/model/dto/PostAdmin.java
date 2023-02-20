package com.campingmapping.team4.spring.t433forum.model.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAdmin{
	
	private Integer postid;
	private UUID uid;
	private String title;
	private String content;
	private String picture;
	private Integer people;
	private Integer price;
	private String county;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startdate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date enddate;
	private Integer score;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date releasedate;
	private Integer userlike;
	private Integer userunlike;
	private Integer postreport;
	private UUID informantuid;
	private Integer posthide;
}
