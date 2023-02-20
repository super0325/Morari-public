package com.campingmapping.team4.spring.t436mall.model.entity;

import lombok.Data;

@Data
public class Result<T> {

	T Data;
	
	String message;
	
	
	
}
