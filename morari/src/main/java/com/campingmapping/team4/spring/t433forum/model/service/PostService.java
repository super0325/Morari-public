package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostRepository;
import com.campingmapping.team4.spring.t433forum.model.dto.PostAdmin;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtService jwtService;

	// 查單一貼文
	public PostAdmin getPostById(Integer id) {
		Post post = postRepository.findById(id).get();
		PostAdmin postAdmin = new PostAdmin();
		postAdmin.setPostid(post.getPostid());
		postAdmin.setUid(post.getUserprofiles().getUid());
		postAdmin.setTitle(post.getTitle());
		postAdmin.setContent(post.getContent());
		postAdmin.setPicture(post.getPicture());
		postAdmin.setPeople(post.getPeople());
		postAdmin.setPrice(post.getPrice());
		postAdmin.setCounty(post.getCounty());
		postAdmin.setStartdate(post.getStartdate());
		postAdmin.setEnddate(post.getEnddate());
		postAdmin.setScore(post.getScore());
		postAdmin.setReleasedate(post.getReleasedate());
		postAdmin.setUserlike(post.getUserlike());
		postAdmin.setUserunlike(post.getUserunlike());
		postAdmin.setPostreport(post.getPostreport());
		if(post.getInformantuserprofiles() != null) {
			postAdmin.setInformantuid(post.getInformantuserprofiles().getUid());
		}
		postAdmin.setPosthide(post.getPosthide());
		return postAdmin;
	}

	// 查所有貼文
	public List<PostAdmin> getAllPost() {
		List<PostAdmin> list = new ArrayList<>();
		postRepository.findAll().forEach(post -> {
			PostAdmin postAdmin = new PostAdmin();
			postAdmin.setPostid(post.getPostid());
			postAdmin.setUid(post.getUserprofiles().getUid());
			postAdmin.setTitle(post.getTitle());
			postAdmin.setContent(post.getContent());
			postAdmin.setPicture(post.getPicture());
			postAdmin.setPeople(post.getPeople());
			postAdmin.setPrice(post.getPrice());
			postAdmin.setCounty(post.getCounty());
			postAdmin.setStartdate(post.getStartdate());
			postAdmin.setEnddate(post.getEnddate());
			postAdmin.setScore(post.getScore());
			postAdmin.setReleasedate(post.getReleasedate());
			postAdmin.setUserlike(post.getUserlike());
			postAdmin.setUserunlike(post.getUserunlike());
			postAdmin.setPostreport(post.getPostreport());
			if(post.getInformantuserprofiles() != null) {
				postAdmin.setInformantuid(post.getInformantuserprofiles().getUid());
			}
			postAdmin.setPosthide(post.getPosthide());
			list.add(postAdmin);
		});
		return list;
	}

	// 查會員貼文
	public List<Post> getUserPost(UUID userId) {
		UserProfiles uProfiles = userRepository.findById(userId).get();
		return postRepository.findByUserprofilesOrderByReleasedateDesc(uProfiles);
	}

	// 查非隱藏貼文
	public List<Post> getNonHidePost() {
		return postRepository.findByPosthideOrderByReleasedateDesc(0);
	}

	// 查會員非隱藏貼文
	public List<Post> getUserNonHidePost(UUID userId) {
		UserProfiles uProfiles = userRepository.findById(userId).get();
		return postRepository.findByUserprofilesAndPosthideOrderByReleasedateDesc(uProfiles, 0);
	}
	
	// 查隱藏貼文
	public List<Post> getHidePost() {
		return postRepository.findByPosthideOrderByReleasedateDesc(1);
	}

	// 查檢舉貼文
	public List<Post> getReportPost() {
		return postRepository.findByPostreport(1);
	}

	// 新增
	public Post insert(Post post) {
		UUID uid = jwtService.getUId(request);
		UserProfiles userProfiles = userRepository.findById(uid).get();
		post.setUserprofiles(userProfiles);
		post.setReleasedate(new Date());
		post.setUserlike(0);
		post.setUserunlike(0);
		post.setPostreport(0);
		post.setInformantuserprofiles(null);
		post.setPosthide(0);
		return postRepository.save(post);
	}

	// 修改
	public Post update(Post post) {
		post.setReleasedate(new Date());
		post.setPostreport(0);
		return postRepository.save(post);
	}

	// 喜歡
	public Post likePost(Integer postid) {
		Post post = postRepository.findById(postid).get();
		Integer userlike = post.getUserlike();
		userlike += 1;
		post.setUserlike(userlike);
		return postRepository.save(post);
	}

	public Post likePost(Integer postid, Boolean b) {
		Post post = postRepository.findById(postid).get();
		Integer userlike = post.getUserlike();
		Integer userunlike = post.getUserunlike();
		userlike += 1;
		userunlike -= 1;
		post.setUserlike(userlike);
		post.setUserunlike(userunlike);
		return postRepository.save(post);
	}

	// 不喜歡
	public Post unlikePost(Integer postid) {
		Post post = postRepository.findById(postid).get();
		Integer userUnlike = post.getUserunlike();
		userUnlike += 1;
		post.setUserunlike(userUnlike);
		return postRepository.save(post);
	}

	public Post unlikePost(Integer postid, Boolean b) {
		Post post = postRepository.findById(postid).get();
		Integer userlike = post.getUserlike();
		Integer userUnlike = post.getUserunlike();
		userlike -= 1;
		userUnlike += 1;
		post.setUserlike(userlike);
		post.setUserunlike(userUnlike);
		return postRepository.save(post);
	}

	// 檢舉
	public Post report(Integer postid) {
		Post post = postRepository.findById(postid).get();
		UUID uid = jwtService.getUId(request);
		UserProfiles userProfiles = userRepository.findById(uid).get();
		post.setPostreport(1);
		post.setInformantuserprofiles(userProfiles);
		return postRepository.save(post);
	}

	// 取消檢舉
	public Post cancelReport(Integer postid) {
		Post post = postRepository.findById(postid).get();
		post.setPostreport(0);
		post.setInformantuserprofiles(null);
		return postRepository.save(post);
	}

	// 隱藏
	public Post hide(Integer postid) {
		Post post = postRepository.findById(postid).get();
		post.setPosthide(1);
		return postRepository.save(post);
	}

	// 取消隱藏
	public Post cancelHide(Integer postid) {
		Post post = postRepository.findById(postid).get();
		post.setPosthide(0);
		return postRepository.save(post);
	}
}
