package com.campingmapping.team4.spring.t433forum.model.entity;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voteid")
	private int voteid;
	@Column(name = "votename")
	private String votename;
	// 0表示隱藏 1表示投票中 2表示已結束
	@Column(name = "voting")
	private int voting;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles winner;
}
