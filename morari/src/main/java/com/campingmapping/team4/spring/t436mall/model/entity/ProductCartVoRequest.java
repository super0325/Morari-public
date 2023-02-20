package com.campingmapping.team4.spring.t436mall.model.entity;

import lombok.Data;

@Data
public class ProductCartVoRequest {

	public Integer id;
	// 購物車編號 不顯示(pk)
	public String userid;
	// 會員 ID
	public Integer pdid;
	// 產品編號(fk)
	public Integer ctqty;
	// 數量
	public String pdpicture;
	// 照片 vinbinary
	public Integer pdprice;
	// 價位
	public String pdname;
	// 產品名稱
	public Integer pdinventory;
}
