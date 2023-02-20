package com.campingmapping.team4.spring.t411team.model.entity;

import java.util.Date;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "messagearea")
public class MessageArea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageid")
	private Integer messageid;
	
	@ManyToOne
	@JoinColumn(name = "initiatingnum")
	private Initiating initiating;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;
	
	@Column(name = "postday")
	private Date postday;
	
	@Column(name = "message")
	private String message; 
	
}
