package com.campingmapping.team4.spring.t436mall.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productorderdetail")
public class ProductOrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	// 訂單詳情序號 不顯示(pk)
	@Column(name = "pdorderid")
	private String pdorderid;
	// 訂單編號
	@Column(name = "pdid")
	private Integer pdid;
	// 產品編號
	@Column(name = "pdqty")
	private Integer pdqty;
	// 數量

}
