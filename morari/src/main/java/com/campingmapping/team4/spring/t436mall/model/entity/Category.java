package com.campingmapping.team4.spring.t436mall.model.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//商品列表
@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "pdid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pdid;
	// 產品編號(pk)
	@Column(name = "userid")
	private String userid;
	// 會員 ID
	@Column(name = "pdname")
	private String pdname;
	// 產品名稱
	@Column(name = "pdtitle")
	private String pdtitle;
	// 品牌名稱
	@Column(name = "pdcontent", length = 500)
	private String pdcontent;
	// 產品規格
	@Column(name = "pdtype")
	private String pdtype;
	// 產品類型
	@Column(name = "pdpicture")
	private String pdpicture;
	// 照片 vinbinary
	@Column(name = "pdprice")
	private Integer pdprice;
	// 價位
	@Column(name = "pdinventory")
	private Integer pdinventory;
	// 庫存數量
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "pddate")
	private Date pddate;
	// 商品建立日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "pdlastupdate")
	private Date pdlastupdate;
	// 商品更新日期

}
