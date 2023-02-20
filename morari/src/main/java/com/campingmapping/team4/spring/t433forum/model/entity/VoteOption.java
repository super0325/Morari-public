package com.campingmapping.team4.spring.t433forum.model.entity;

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
@Table(name = "voteoption")
public class VoteOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "optionid")
	private Integer optionid;
	@Column(name = "optionname")
	private String optionname;
	
	@ManyToOne
	@JoinColumn(name = "voteid")
	private Vote vote;

}