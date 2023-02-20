package com.campingmapping.team4.spring.t433forum.controller.api;


import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.t433forum.model.dao.repository.PostRepository;
import com.campingmapping.team4.spring.t433forum.model.dto.PostAdmin;
import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostComment;
import com.campingmapping.team4.spring.t433forum.model.service.PostCommentService;
import com.campingmapping.team4.spring.t433forum.model.service.PostService;
import com.campingmapping.team4.spring.utils.config.GoogleFileUtil;

@RestController
public class InsertPostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private PostRepository postRepository;
	
	// 新增貼文
	@PostMapping("/insertpost.controller")
	public String insertPost(@RequestBody Post post) {
		postService.insert(post);
		return "true";
	}
	
	// 新增留言
	@PostMapping("/insertpostcomment.controller/{postid}")
	public String insertPostComment(@RequestBody PostComment postComment, @PathVariable Integer postid) {
		PostAdmin postAdmin = postService.getPostById(postid);
		postComment.setPost(postRepository.findById(postAdmin.getPostid()).get());
		postCommentService.insert(postComment);
		return "true";
	}
	
	// 新增照片
	@PostMapping("/insertpicture.controller")
	public static String insertPicture(@RequestBody MultipartFile file) {
		String fileName = "post" + UUID.randomUUID().toString();
		try {
			return GoogleFileUtil.uploadFile(fileName, file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
