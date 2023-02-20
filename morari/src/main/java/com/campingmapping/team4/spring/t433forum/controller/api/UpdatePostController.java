package com.campingmapping.team4.spring.t433forum.controller.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.service.impl.UserServiceImpl;
import com.campingmapping.team4.spring.t433forum.model.dto.PostAdmin;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostLike;
import com.campingmapping.team4.spring.t433forum.model.service.PostCommentService;
import com.campingmapping.team4.spring.t433forum.model.service.PostLikeService;
import com.campingmapping.team4.spring.t433forum.model.service.PostService;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UpdatePostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private PostLikeService postLikeService;
	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserServiceImpl userServiceImpl;

	// 修改貼文
	@PutMapping("/updatepost.controller")
	public String updatePost(@RequestBody Post post) {
		Integer postid = post.getPostid();
		PostAdmin postById = postService.getPostById(postid);
		post.setUserprofiles(userRepository.findById(postById.getUid()).get());
		post.setUserlike(postById.getUserlike());
		post.setUserunlike(postById.getUserunlike());
		post.setPostreport(postById.getPostreport());
		post.setInformantuserprofiles(userRepository.findById(postById.getInformantuid()).get());
		post.setPosthide(postById.getPosthide());
		postService.update(post);
		return "true";
	}
	
	// 檢舉貼文
	@PutMapping("/reportpost.controller/{postid}")
	public String reportPost(@PathVariable Integer postid) {
		postService.report(postid);
		return "true";
	}
	
	// 取消檢舉貼文
	@PutMapping("/cancelreportpost.controller/{postid}")
	public String cancelReportPost(@PathVariable Integer postid) {
		postService.cancelReport(postid);
		return "true";
	}
	
	// 封鎖檢舉者
	@PutMapping("/lockaccount.controller/{uid}")
	public Boolean lockaccount(@PathVariable UUID uid) {
		// 使用者狀態：true可用 false封鎖
		return userServiceImpl.updateaccountlocked(uid, false);
	}
	
	// 隱藏貼文
	@PutMapping("/hidepost.controller/{postid}")
	public String hidePost(@PathVariable Integer postid) {
		postService.hide(postid);
		return "true";
	}
	
	// 取消隱藏貼文
	@PutMapping("/cancelhidepost.controller/{postid}")
	public String cancelHidePost(@PathVariable Integer postid) {
		postService.cancelHide(postid);
		return "true";
	}
	
	// 喜歡貼文
	@PutMapping("/likepost.controller/{postid}")
	public String likePost(@PathVariable Integer postid) {
		UUID uid = jwtService.getUId(request);
		PostLike postLike = postLikeService.showPostLike(postid, uid);
		if(postLike == null) {
			postService.likePost(postid);
			postLikeService.insertPostLike(postid, uid, 1);
		}else if(postLike.getLikeorunlike() == 1) {
			return "false";
		}else if(postLike.getLikeorunlike() == 2) {
			postService.likePost(postid, true);
			postLikeService.updatePostLike(postLike);
		}
		return "true";
	}
	
	// 不喜歡貼文
	@PutMapping("/unlikepost.controller/{postid}")
	public String unlikePost(@PathVariable Integer postid) {
		UUID uid = jwtService.getUId(request);
		PostLike postLike = postLikeService.showPostLike(postid, uid);
		if(postLike == null) {
			postService.unlikePost(postid);
			postLikeService.insertPostLike(postid, uid, 2);
		}else if(postLike.getLikeorunlike() == 2) {
			return "false";
		}else if(postLike.getLikeorunlike() == 1) {
			postService.unlikePost(postid, true);
			postLikeService.updatePostLike(postLike);
		}
		return "true";
	}
	
	// 檢舉留言
	@PutMapping("/reportpostcomment.controller/{postcommentid}")
	public String reportPostComment(@PathVariable Integer postcommentid) {
		postCommentService.reportPostComment(postcommentid);
		return "true";
	}
		
	// 取消檢舉留言
	@PutMapping("/cancelreportpostcomment.controller/{postcommentid}")
	public Boolean cancelReportPostComment(@PathVariable Integer postcommentid) {
		postCommentService.cancelReportPostComment(postcommentid);
		return true;
	}
		
	// 隱藏留言
	@PutMapping("/hidepostcomment.controller/{postcommentid}")
	public String hidePostComment(@PathVariable Integer postcommentid) {
		postCommentService.hidePostComment(postcommentid);
		return "true";
	}
		
	// 取消隱藏留言
	@PutMapping("/cancelhidepostcomment.controller/{postcommentid}")
	public String cancelHidePostComment(@PathVariable Integer postcommentid) {
		postCommentService.cancelHidePostComment(postcommentid);
		return "true";
	}
}
