package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	// 依user查貼文
	public List<Post> findByUserprofilesOrderByReleasedateDesc(UserProfiles uProfiles);
	
	// 依隱藏狀態查貼文
	public List<Post> findByPosthideOrderByReleasedateDesc(Integer hide);
	
	// 依user查貼文
	public List<Post> findByUserprofilesAndPosthideOrderByReleasedateDesc(UserProfiles uProfiles, Integer hide);
	
	// 依檢舉狀態查貼文
	public List<Post> findByPostreport(Integer report);
}
