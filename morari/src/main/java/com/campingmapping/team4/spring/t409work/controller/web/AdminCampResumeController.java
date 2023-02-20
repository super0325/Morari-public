package com.campingmapping.team4.spring.t409work.controller.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t409work.model.entity.ResumeBean;
import com.campingmapping.team4.spring.t409work.model.service.ResumeService;

//resume(營主)的後台
@Controller
@RequestMapping("/admin/user/resume")
public class AdminCampResumeController {

	@Autowired
	private ResumeService rService;

	// 啟動我的首頁
	@GetMapping("/resumeStartCrud.controller/{rackid}")
	public String processMainAction1() {
		return "work/admin/camp/campResume";
	}

	// 透過rackid找資料後給前端
	@PostMapping("/userResumeRackId.controller/{rackID}")
	@ResponseBody
	public Collection<ResumeBean> processAction4(@PathVariable Integer rackID) {
		 Collection<ResumeBean> findRid = rService.findRid(rackID);
		return findRid;
	}
}
