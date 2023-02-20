package com.campingmapping.team4.spring.t436mall.model.entity;

public interface ProductCartVo {

	Integer getid();
	// 購物車編號 不顯示(pk)
	String getuserid();
	// 會員 ID
	Integer getpdid();
	// 產品編號(fk)
	Integer getctqty();
	// 數量
	String getpdpicture();
	// 照片 vinbinary
	Integer getpdprice();
	// 價位
	String getpdname();
	// 產品名稱
	Integer getpdinventory();

}
