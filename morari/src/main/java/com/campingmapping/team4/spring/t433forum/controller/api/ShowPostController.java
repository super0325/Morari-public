package com.campingmapping.team4.spring.t433forum.controller.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.campingmapping.team4.spring.t433forum.model.dto.PostAdmin;
import com.campingmapping.team4.spring.t433forum.model.dto.ShowPostComment;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.service.PostCommentService;
import com.campingmapping.team4.spring.t433forum.model.service.PostService;

@RestController
public class ShowPostController {

	@Autowired
	private PostService postService;
	@Autowired
	private PostCommentService postCommentService;

	// 查單一貼文
	@GetMapping("/showpostbyid.controller/{postid}")
	public PostAdmin processShowPostById(@PathVariable(name = "postid") Integer postId) {
		return postService.getPostById(postId);
	}

	// 查所有貼文
	@GetMapping("/showall.controller")
	public List<PostAdmin> processShowAllPost() {
		return postService.getAllPost();
	}

	// 查會員貼文
	@GetMapping("/showpostbyuserid.controller/{userid}")
	public List<Post> processShowPostByUserId(@PathVariable(name = "userid") UUID userId) {
		return postService.getUserPost(userId);
	}

	// 查非隱藏貼文
	@GetMapping("/shownonhidepost.controller")
	public List<Post> processShowNonHidePost() {
		return postService.getNonHidePost();
	}
	
	// 查會員非隱藏貼文
	@GetMapping("/showpostnonhidebyuserid.controller/{userid}")
	public List<Post> processShowPostNonHideByUserId(@PathVariable(name = "userid") UUID userId) {
		return postService.getUserNonHidePost(userId);
	}

	// 查隱藏貼文
	@GetMapping("/showhidepost.controller")
	public List<Post> processShowHidePost() {
		return postService.getHidePost();
	}

	// 查檢舉貼文
	@GetMapping("/showreportpost.controller")
	public List<Post> processShowReportPost() {
		return postService.getReportPost();
	}

	// 查貼文所有留言
	@GetMapping("/showpostcommentbypostid.controller/{postid}")
	public List<ShowPostComment> processShowPostCommentByPostId(@PathVariable(name = "postid") Integer postId) {
		return postCommentService.getPostCommentByPostId(postId);
	}

	// 查貼文所有非隱藏留言+分頁
	@GetMapping("/showpostcommentnonhidebypostid.controller/{postid}/{page}")
	public List<ShowPostComment> processShowPostCommentNonHideByPostId(@PathVariable Integer postid,
			@PathVariable Integer page) {
		int pageSize = 3; // 每頁顯示的筆數
		Pageable pageable = PageRequest.of(page - 1, pageSize); // 設定顯示頁碼 及 每頁筆數
		Page<ShowPostComment> postCommentNonHideByPostId = postCommentService.getPostCommentNonHideByPostId(postid,
				pageable);
		return postCommentNonHideByPostId.getContent();
	}

	// 查熱門貼文
	@GetMapping("/showhotpost.controller")
	public Post processShowHotPost() {
		int max = 0;
		Post hotPost = null;
		List<Post> nonHidePost = postService.getNonHidePost();
		for (Post post : nonHidePost) {
			List<ShowPostComment> postCommentByPostId = postCommentService.getPostCommentByPostId(post.getPostid());
			int count = post.getUserlike() + postCommentByPostId.size();
			if (count > max) {
				max = count;
				hotPost = post;
			}
		}
		return hotPost;
	}
}
