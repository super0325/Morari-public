package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostLikeRepository;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostRepository;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostLike;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostLikeService {
	@Autowired
	private PostLikeRepository postLikeRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	
	// 查喜歡或不喜歡貼文
	public PostLike showPostLike(int postid, UUID uid) {
		Post post = new Post();
		post.setPostid(postid);
		UserProfiles userProfiles = new UserProfiles();
		userProfiles.setUid(uid);
		return postLikeRepository.findByPostAndUserprofiles(post, userProfiles);
	}
	
	// 新增喜歡或不喜歡貼文
	public void insertPostLike(int postid, UUID uid, int likeOrUnlike) {
		Post post = postRepository.findById(postid).get();
		UserProfiles userProfiles = userRepository.findById(uid).get();
		PostLike postLike = new PostLike();
		postLike.setPost(post);
		postLike.setUserprofiles(userProfiles);
		postLike.setLikeorunlike(likeOrUnlike);
		postLikeRepository.save(postLike);
	}
	
	// 修改喜歡或不喜歡貼文
	public void updatePostLike(PostLike postLike) {
		int likeOrUnlike = postLike.getLikeorunlike();
		if(likeOrUnlike == 1) {
			postLike.setLikeorunlike(2);
		}else if(likeOrUnlike == 2) {
			postLike.setLikeorunlike(1);
		}
		postLikeRepository.save(postLike);
	}
}
