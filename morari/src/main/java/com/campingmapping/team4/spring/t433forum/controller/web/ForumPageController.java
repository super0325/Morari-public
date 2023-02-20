package com.campingmapping.team4.spring.t433forum.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ForumPageController {
	
	@GetMapping({ "", "/" })
	public String forumIndex() {
		return "forum/guest/index";
	}

	// 顯示個人貼文頁面
	@GetMapping("/showpostbyuserid.controller")
	public String processShowPostByUserId() {
		return "forum/guest/userpost";
	}
	
	// 顯示新增貼文頁面
	@GetMapping("/showinsert.controller")
	public String processShowInsert() {
		return "forum/guest/newpost";
	}
	
	// 顯示貼文內容
	@GetMapping("/showpost.controller/*")
	public String processShowPost() {
		return "forum/guest/showpost";
	}
	
	// 會員顯示貼文內容
	@GetMapping("/usershowpost.controller/*")
	public String processUserShowPost() {
		return "forum/guest/usershowpost";
	}
	
	// 顯示修改貼文
	@GetMapping("/showupdate.controller/*")
	public String processShowUpdate() {
		return "forum/guest/updatepost";
	}
	
	// 顯示投票頁面
	@GetMapping("/showvote.controller")
	public String processShowVote() {
		return "forum/guest/vote";
	}
}
