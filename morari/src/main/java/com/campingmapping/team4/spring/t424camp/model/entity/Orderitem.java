package com.campingmapping.team4.spring.t424camp.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "camporderitem")
@Component
public class Orderitem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDERITEMID")
	private Integer orderitemID;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FKORDERID")
	private Order campOrder;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FKSITEID")
	private Site site;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "GOINGTIME")
	private Date goingTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LEAVINGTIME")
	private Date leavingTime;
	
	@Column(name = "NUMBERS")
	private Integer numbers;
	
	@Column(name = "UNITPRICE")
	private Integer unitPrice;

	
	public Orderitem() {
		super();
	}
	

	public Orderitem(Site site, Date goingTime, Date leavingTime, Integer numbers, Integer unitPrice) {
		super();
		this.site = site;
		this.goingTime = goingTime;
		this.leavingTime = leavingTime;
		this.numbers = numbers;
		this.unitPrice = unitPrice;
	}


	public Orderitem(Integer orderitemID, Site site, Date goingTime, Date leavingTime,
			Integer numbers, Integer unitPrice) {
		super();
		this.orderitemID = orderitemID;
		this.site = site;
		this.goingTime = goingTime;
		this.leavingTime = leavingTime;
		this.numbers = numbers;
		this.unitPrice = unitPrice;
	}

	
	public Integer getOrderitemID() {
		return orderitemID;
	}

	public void setOrderitemID(Integer orderitemID) {
		this.orderitemID = orderitemID;
	}

	public Order getCampOrder() {
		return campOrder;
	}

	public void setCampOrder(Order campOrder) {
		this.campOrder = campOrder;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Date getGoingTime() {
		return goingTime;
	}

	public void setGoingTime(Date goingTime) {
		this.goingTime = goingTime;
	}

	public Date getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(Date leavingTime) {
		this.leavingTime = leavingTime;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
