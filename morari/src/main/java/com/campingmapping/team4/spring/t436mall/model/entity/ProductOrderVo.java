package com.campingmapping.team4.spring.t436mall.model.entity;

public interface ProductOrderVo {

	// p.id, p.userid, d.pdid, d.pdqty, c.pdpicture, c.pdprice, c.pdname,text
	String getid();

	String getuserid();

	String getdatetime();

	Integer getpdid();

	Integer getmoney();

	String getodshippingaddress();

	String getodrecipient();

	String getodrecipientphone();

	Integer getpdqty();

	String getpdpicture();

	Integer getpdprice();

	String getpdname();
}
