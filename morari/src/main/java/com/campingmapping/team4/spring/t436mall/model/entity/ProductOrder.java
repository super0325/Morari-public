package com.campingmapping.team4.spring.t436mall.model.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "productorder")
public class ProductOrder {

	@Id
	@Column(name = "id")
	private String id;
	// 訂單編號(自動產生亂碼)(pk)
	@Column(name = "userid")
	private String userid;
	// 會員 ID
	@Column(name = "odstatus")
	private String odstatus;
	// 訂單狀態
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "datetime")
	private Date datetime;
	// 下訂單日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "odshippingdatetime")
	private Date odshippingdatetime;
	// 出貨日期
	@Column(name = "money")
	private Integer money;
	// 總計
	@Column(name = "odshippingaddress")
	private String odshippingaddress;
	// 運送位置
	@Column(name = "odrecipient")
	private String odrecipient;
	// 收件人名稱
	@Column(name = "odrecipientphone")
	private String odrecipientphone;
	// 收件人電話

}
