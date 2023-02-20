package com.campingmapping.team4.spring.t436mall.model.entity;

public interface ProductOrderDetailVo {

	// p.pdorderid, p.pdid, p.pdqty, c.pdpicture, c.pdprice, c.pdname
	String getpdorderid();

	Integer getpdid();

	Integer getpdqty();

	String getpdpicture();

	Integer getpdprice();

	String getpdname();
}