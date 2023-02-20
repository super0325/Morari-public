package com.campingmapping.team4.spring.t411team.model.entity;

import java.util.Collection;
import java.util.Date;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "initiating")
public class Initiating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "initiatingnum")
	private Integer initiatingnum;
	
	@JsonIgnoreProperties("initiating")
	@OneToMany(fetch = FetchType.LAZY,mappedBy="initiating")//設一個外來建的集合
	@JsonIgnore
	private Collection<Thundsup> thundsups;
	
	@JsonIgnoreProperties("initiating")
	@OneToMany(fetch = FetchType.LAZY,mappedBy="initiating")//設一個外來建的集合
	@JsonIgnore
	private Collection<MessageArea> messageAreas;

	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;
	
	@Column(name = "postdate")
	private Date postdate;

	@Column(name = "startdate")
	private Date startdate;

	@Column(name = "enddate")
	private Date enddate;

	@Column(name = "currentnum")
	private Integer currentnum;

	@Column(name = "acceptablenum")
	private Integer acceptablenum;

	@Column(name = "camparea")
	private String camparea;

	@Column(name = "pair")
	private Integer pair;
	
	@Transient
	private String gender;
	
	@Transient
	private String[] lodging;
	
	@Transient
	private String[] equipment;
	
	@Column(name = "tag")
	private String tag;
	
	@Column(name = "introduction")
	private String introduction;
	
	@Column(name = "viewingcount")
	private int viewingCount;
	
	@Column(name = "thumbsup")
	private Integer thumbsUp;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "applycount")
	private int applycount;
	
	@Column(name = "picture")
	private String picture;

}
