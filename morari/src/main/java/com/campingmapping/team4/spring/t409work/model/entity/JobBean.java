package com.campingmapping.team4.spring.t409work.model.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"resumes"})
@Entity
@Table(name = "job")
public class JobBean {
	
	@ManyToMany(mappedBy = "jobs")
	@JsonIgnore
	private Collection<ResumeBean> resumes = new HashSet<>();//外來鍵集合

	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;// 會員

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rackid")
	private Integer rackid;// 刊登編號
	
	@Column(name = "campname")
	private String campname;// 營地名稱
	@Column(name = "place")
	private String place;// 地點
	@Column(name = "type")
	private String type;// 職缺種類
	@Column(name = "job")
	private String job;// 職缺
	@Column(name = "salary")
	private String salary;// 薪資
	@Column(name = "quantity")
	private Integer quantity;// 人數
	@Column(name = "date")
	private String date;// 上班日期
	@Column(name = "time")
	private String time;// 上班時段
	@Column(name = "remark")
	private String remark;// 備註
	@Column(name = "rackup")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date rackup;// 上架日期
	@Column(name = "img")
	private String img;// 照片

}