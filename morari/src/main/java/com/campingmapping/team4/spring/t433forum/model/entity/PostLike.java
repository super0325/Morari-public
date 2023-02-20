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
@Table(name = "postlike")
public class PostLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postlikeid")
	private int postlikeid;
	
	@ManyToOne
	@JoinColumn(name = "postid")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private UserProfiles userprofiles;
	
	// 喜歡1 不喜歡2
	@Column(name = "likeorunlike")
	private int likeorunlike;
}
