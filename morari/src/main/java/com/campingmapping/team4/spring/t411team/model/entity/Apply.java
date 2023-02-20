package com.campingmapping.team4.spring.t411team.model.entity;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "apply")
public class Apply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applyid")
	private Integer applyid;
	
	@ManyToOne
	@JoinColumn(name = "initiatingnum")
	private Initiating initiating;
	
	@Transient
	private int initiatingnumber;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;
	
	@Column(name = "applypeople")
	private int applypeople;
	
	@Column(name = "responsestate")
	private int responsestate;
	
	@Column(name = "pairstate")
	private int pairstate;
	
	@Column(name = "applymessage")
	private String applymessage;
	
	@Column(name = "responsemessage")
	private String responsemessage;
	
	@Column(name = "limit")
	private int limit;
	
}
