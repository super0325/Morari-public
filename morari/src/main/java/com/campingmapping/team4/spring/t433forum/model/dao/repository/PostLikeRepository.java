package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
	
	// 依post及user查喜歡或不喜歡
	public PostLike findByPostAndUserprofiles(Post post, UserProfiles userprofiles);
}
