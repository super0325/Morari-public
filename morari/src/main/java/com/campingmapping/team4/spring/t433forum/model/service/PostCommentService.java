package com.campingmapping.team4.spring.t433forum.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostCommentRepository;
import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostRepository;
import com.campingmapping.team4.spring.t433forum.model.dto.ShowPostComment;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostComment;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class PostCommentService {
	@Autowired
	private PostCommentRepository postCommentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private HttpServletRequest request;

	// 依貼文查留言
	public List<ShowPostComment> getPostCommentByPostId(Integer postid) {
		Post post = postRepository.findById(postid).get();
		List<ShowPostComment> list = new ArrayList<>();
		postCommentRepository.findByPost(post).forEach(postcomment -> {
			ShowPostComment showPostComment = new ShowPostComment();
			showPostComment.setPostcommentid(postcomment.getPostcommentid());
			showPostComment.setPostid(postcomment.getPost().getPostid());
			showPostComment.setUid(postcomment.getUserprofiles().getUid());
			showPostComment.setPostcomment(postcomment.getPostcomment());
			showPostComment.setPostcommentreport(postcomment.getPostcommentreport());
			if(postcomment.getInformantuserprofiles() != null) {
				showPostComment.setInformantuid(postcomment.getInformantuserprofiles().getUid());
			}	
			showPostComment.setPostcommenthide(postcomment.getPostcommenthide());
			list.add(showPostComment);
		});
		return list;
	}
	
	// 依貼文查非隱藏留言+分頁
	public Page<ShowPostComment> getPostCommentNonHideByPostId(Integer postid, Pageable pageable) {
		Post post = postRepository.findById(postid).get();
		List<ShowPostComment> list = new ArrayList<>();
		postCommentRepository.findByPostAndPostcommenthide(post, 0, pageable).forEach(postcomment -> {
			ShowPostComment showPostComment = new ShowPostComment();
			showPostComment.setPostcommentid(postcomment.getPostcommentid());
			showPostComment.setPostid(postcomment.getPost().getPostid());
			showPostComment.setUid(postcomment.getUserprofiles().getUid());
			showPostComment.setPostcomment(postcomment.getPostcomment());
			showPostComment.setPostcommentreport(postcomment.getPostcommentreport());
			if(postcomment.getInformantuserprofiles() != null) {
				showPostComment.setInformantuid(postcomment.getInformantuserprofiles().getUid());
			}
			showPostComment.setPostcommenthide(postcomment.getPostcommenthide());
			list.add(showPostComment);
		});
		PageImpl<ShowPostComment> pageImpl = new PageImpl<>(list, pageable, list.size());
		return pageImpl;
	}

	// 新增留言
	public PostComment insert(PostComment postComment) {

		UUID uid = jwtService.getUId(request);

		UserProfiles userProfiles = userRepository.findById(uid).get();
		postComment.setUserprofiles(userProfiles);

		postComment.setPostcommentreport(0);
		postComment.setInformantuserprofiles(null);
		postComment.setPostcommenthide(0);
		return postCommentRepository.save(postComment);
	}

	// 隱藏留言
	public PostComment hidePostComment(Integer postcommentid) {
		PostComment postComment = postCommentRepository.findById(postcommentid).get();
		postComment.setPostcommenthide(1);
		return postCommentRepository.save(postComment);
	}

	// 取消隱藏留言
	public PostComment cancelHidePostComment(Integer postcommentid) {
		PostComment postComment = postCommentRepository.findById(postcommentid).get();
		postComment.setPostcommenthide(0);
		return postCommentRepository.save(postComment);
	}
	
	// 檢舉留言
	public PostComment reportPostComment(Integer postcommentid) {
		PostComment postComment = postCommentRepository.findById(postcommentid).get();
		UUID uid = jwtService.getUId(request);
		UserProfiles userProfiles = userRepository.findById(uid).get();
		postComment.setPostcommentreport(1);
		postComment.setInformantuserprofiles(userProfiles);
		return postCommentRepository.save(postComment);
	}

	// 取消檢舉留言
	public PostComment cancelReportPostComment(Integer postcommentid) {
		PostComment postComment = postCommentRepository.findById(postcommentid).get();
		postComment.setPostcommentreport(0);
		postComment.setInformantuserprofiles(null);
		return postCommentRepository.save(postComment);
	}
}
