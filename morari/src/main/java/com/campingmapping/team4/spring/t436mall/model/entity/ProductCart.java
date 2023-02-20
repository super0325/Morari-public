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
@Table(name = "productcart")
public class ProductCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	// 購物車編號 不顯示(pk)
	@Column(name = "userid")
	private String userid;
	// 會員 ID
	@Column(name = "pdid")
	private Integer pdid;
	// 產品編號(fk)
	@Column(name = "ctqty")
	private Integer ctqty;
	// 數量

}
