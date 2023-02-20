package com.campingmapping.team4.spring.t409work.controller.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t409work.model.entity.ResumeBean;
import com.campingmapping.team4.spring.t409work.model.service.ResumeService;


// resume(管理者)的後台
@Controller
@RequestMapping("/admin/resume")
public class AdminManagerResumeController {

	@Autowired
	private ResumeService rService;


	// 啟動我的首頁
	@GetMapping("/resumeCrud.controller")
	public String processMainAction1() {
		return "work/admin/manager/resumeCrud";
	}

	// 啟動履歷insert
	@GetMapping("/startResumeInsert.controller/{uid}")
	public String processMainAction2() {
		return "work/admin/manager/resumeInsert";
	}

	// 啟動select
	@PostMapping("/select.controller")
	public String processMainAction3() {
		return "work/admin/manager/resumeSelect";
	}

	// 啟動update
	@GetMapping("/update.controller/{number}")
	public String processMainAction4() {
		return "work/admin/manager/resumeUpdate";
	}

	// 啟動mail輸入
	@GetMapping("/startMail.controller/{m}")
	public String processMainAction5() {
		return "work/admin/manager/mailInsert";
	}

	// 刪除
	@DeleteMapping("/resumeDelete.controller/{number}")
	@ResponseBody
	public String processDeleteAction(@PathVariable Integer number) {
		rService.deleteById(number);
		return "刪除成功";
	}

	// 修改
	@PutMapping("/resumeUpdate.controller/{number}")
	@ResponseBody
	public List<ResumeBean> processUpdateAction(@RequestBody ResumeBean rBean, @PathVariable Integer number) {
		
		 ResumeBean updateJob = rService.updateJob(rBean, number);
		 List<ResumeBean> list = Arrays.asList(updateJob);
		return list;
	}

	// 找全部
	@PostMapping("/resumeShowAll.controller")
	@ResponseBody
	public List<ResumeBean> processShowResumeAllAction() {
		List<ResumeBean> result = rService.findAll();
		return result;
	}

	// 透過number找資料後給前端修改
	@PostMapping("/selectNumber.controller/{number}")
	@ResponseBody
	public ResumeBean processAction4(@PathVariable Integer number) {
		ResumeBean result = rService.findById(number);
		return result;
	}

	// 透過uid搜尋resume
	@PostMapping("/resumeSelectUid.controller/{uid}")
	@ResponseBody
	public ResumeBean processSelectUidAction(@PathVariable UUID uid) {
		try {
			ResumeBean result = rService.findByUid(uid);
			if (result == null) {
				return null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
		
	}

	// 透過rackid搜尋(在企業主端秀出來用)
	@PostMapping("/selectRid.controller/{rackid}")
	@ResponseBody
	public Collection<ResumeBean> processSelectRidAction(@PathVariable Integer rackid) {
		Collection<ResumeBean> result = rService.findRid(rackid);
		return result;
	}

	// 新增履歷
	@PostMapping("/resumeInsert.controller/{uid}")
	@ResponseBody
	public List<ResumeBean> processInsertAction2(@RequestBody ResumeBean rBean,@PathVariable("uid") UUID uid) {
		 ResumeBean insert = rService.insert(rBean, uid);
		 List<ResumeBean> list = Arrays.asList(insert);
		return list;
	}

}
