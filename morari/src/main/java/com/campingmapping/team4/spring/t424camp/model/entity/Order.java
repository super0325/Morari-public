package com.campingmapping.team4.spring.t424camp.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "camporder")
@Component
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDERID")
	private Integer orderID;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FKUSERID")
	private UserProfiles userprofiles;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ORDERTIME")
	private Date orderTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "GOINGTIME")
	private Date goingTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "LEAVINGTIME")
	private Date leavingTime;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TOTALPRICE")
	private Integer totalPrice;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FKCAMPID")
	private Camp camp;
	
//	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campOrder")
	private Set<Orderitem> orderitems = new HashSet<Orderitem>();

	
	public Order() {
		super();
	}
	
	public Order(UserProfiles userprofiles, Date orderTime, Date goingTime, Date leavingTime, String status,
			Integer totalPrice, Camp camp, Set<Orderitem> orderitems) {
		super();
		this.userprofiles = userprofiles;
		this.orderTime = orderTime;
		this.goingTime = goingTime;
		this.leavingTime = leavingTime;
		this.status = status;
		this.totalPrice = totalPrice;
		this.camp = camp;
		this.orderitems = orderitems;
	}

	public Order(Integer orderID, UserProfiles userprofiles, Date orderTime, Date goingTime, Date leavingTime,
			String status, Integer totalPrice, Camp camp, Set<Orderitem> orderitems) {
		super();
		this.orderID = orderID;
		this.userprofiles = userprofiles;
		this.orderTime = orderTime;
		this.goingTime = goingTime;
		this.leavingTime = leavingTime;
		this.status = status;
		this.camp = camp;
		this.orderitems = orderitems;
	}

	
	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public UserProfiles getUserprofiles() {
		return userprofiles;
	}

	public void setUserprofiles(UserProfiles userprofiles) {
		this.userprofiles = userprofiles;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Camp getCamp() {
		return camp;
	}

	public void setCamp(Camp camp) {
		this.camp = camp;
	}

	public Set<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(Set<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderID=");
		builder.append(orderID);
		builder.append(", userprofilesName=");
		builder.append(userprofiles.getUsername());
		builder.append(", userprofilesEmail=");
		builder.append(userprofiles.getEmail());
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", goingTime=");
		builder.append(goingTime);
		builder.append(", leavingTime=");
		builder.append(leavingTime);
		builder.append(", status=");
		builder.append(status);
		builder.append(", totalPrice=");
		builder.append(totalPrice);
		builder.append(", campName=");
		builder.append(camp.getCampName());
		builder.append(", location=");
		builder.append(camp.getLocation());
		builder.append(", cityName=");
		builder.append(camp.getCity().getCityName());
		for (Orderitem orderitem : orderitems) {
			builder.append(", siteName=");
			builder.append(orderitem.getSite().getSiteName());
			builder.append(", unitPrice=");
			builder.append(orderitem.getUnitPrice());
			builder.append(", numbers=");
			builder.append(orderitem.getNumbers());
		}
		builder.append("]");
		return builder.toString();
	}

	
}
