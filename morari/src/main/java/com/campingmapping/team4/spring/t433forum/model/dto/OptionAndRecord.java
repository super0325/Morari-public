package com.campingmapping.team4.spring.t433forum.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionAndRecord {

	private Integer optionid;
	private String optionname;
	private Integer recordcount;
}
