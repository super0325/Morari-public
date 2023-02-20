package com.campingmapping.team4.spring.t424camp.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "site")
@Component
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SITEID")
	private Integer siteID;

	@Column(name = "SITENAME")
	private String siteName;

	@Column(name = "SITEPICTURESPATH")
	private String sitePicturesPath;

	@Column(name = "TOTALSITES")
	private Integer totalSites;

	@Column(name = "SITEMONEY")
	private Integer siteMoney;

	// @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FKCAMPID")
	private Camp camp;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "site")
	private Set<Orderitem> orderitems = new HashSet<Orderitem>();

	
	public Site() {
	}

	public Site(Integer siteID, String siteName, String sitePicturesPath, Integer totalSites, Integer siteMoney,
			Camp camp) {
		super();
		this.siteID = siteID;
		this.siteName = siteName;
		this.sitePicturesPath = sitePicturesPath;
		this.totalSites = totalSites;
		this.siteMoney = siteMoney;
		this.camp = camp;
	}

	public Site(String siteName, String sitePicturesPath, Integer totalSites, Integer siteMoney, Camp camp) {
		super();
		this.siteName = siteName;
		this.sitePicturesPath = sitePicturesPath;
		this.totalSites = totalSites;
		this.siteMoney = siteMoney;
		this.camp = camp;
	}

	
	public Integer getSiteID() {
		return siteID;
	}

	public void setSiteID(Integer siteID) {
		this.siteID = siteID;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSitePicturesPath() {
		return sitePicturesPath;
	}

	public void setSitePicturesPath(String sitePicturesPath) {
		this.sitePicturesPath = sitePicturesPath;
	}

	public Integer getTotalSites() {
		return totalSites;
	}

	public void setTotalSites(Integer totalSites) {
		this.totalSites = totalSites;
	}

	public Integer getSiteMoney() {
		return siteMoney;
	}

	public void setSiteMoney(Integer siteMoney) {
		this.siteMoney = siteMoney;
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

}
