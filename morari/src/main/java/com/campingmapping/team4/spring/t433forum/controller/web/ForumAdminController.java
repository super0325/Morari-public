package com.campingmapping.team4.spring.t433forum.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/forum")
public class ForumAdminController {

	@GetMapping("/forumadminindex")
	public String index() {
		return "forum/admin/admin";
	}

	// 顯示新增貼文頁面
	@GetMapping("/showinsertadmin.controller")
	public String processShowInsert() {
		return "forum/admin/newpost";
	}

	// 顯示修改貼文
	@GetMapping("/showupdateadmin.controller/*")
	public String processShowUpdate() {
		return "forum/admin/updatepost";
	}

	// 顯示留言頁面
	@GetMapping("/showcommentadmin.controller/*")
	public String processShowComment() {
		return "forum/admin/comment";
	}

	// 顯示投票管理者頁面
	@GetMapping("/showvoteadmin.controller")
	public String processShowVoteAdmin() {
		return "forum/admin/voteadmin";
	}

	// 顯示新增投票頁面
	@GetMapping("/showinsertvoteadmin.controller")
	public String processShowVote() {
		return "forum/admin/newvote";
	}

	// 顯示投票紀錄頁面
	@GetMapping("/showvoterecordadmin.controller/*")
	public String processShowVoteRecord() {
		return "forum/admin/voterecord";
	}
}
