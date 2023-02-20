package com.campingmapping.team4.spring.t409work.model.entity;

import java.util.Collection;
import java.util.HashSet;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "resume")
public class ResumeBean {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "resume_job", joinColumns = @JoinColumn(name = "number"), 
				inverseJoinColumns = @JoinColumn(name = "rackid"))
	private Collection<JobBean> jobs = new HashSet<>();//外來鍵集合

	@OneToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;// 會員

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number")
	private Integer number;// 履歷編號


	@Column(name = "name")
	private String name;// 姓名
	@Column(name = "age")
	private Integer age;// 年次
	@Column(name = "gender")
	private String gender;// 性別
	@Column(name = "mail")
	private String mail;// 電子郵件
	@Column(name = "phone")
	private String phone;// 電話
	@Column(name = "educational")
	private String educational;// 學歷
	@Column(name = "skill")
	private String skill;// 專業技能

}
