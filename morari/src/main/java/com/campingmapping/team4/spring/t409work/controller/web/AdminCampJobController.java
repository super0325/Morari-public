package com.campingmapping.team4.spring.t409work.controller.web;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t409work.model.entity.JobBean;
import com.campingmapping.team4.spring.t409work.model.service.JobService;
import com.campingmapping.team4.spring.t409work.model.service.MailService;
import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

//job職缺(營主)的後台
@Controller
@RequestMapping("/admin/user/work")
public class AdminCampJobController {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JobService jService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MailService mailService;

	// 啟動我的首頁
	@GetMapping("/startCrud.controller")
	public String processMainAction1() {
		return "work/admin/camp/userCrud";
	}

	// 啟動insert
	@GetMapping("/startInsert.controller/{c}")
	public String processMainAction2() {
		return "work/admin/camp/userInsert";
	}

	// 啟動select
	@PostMapping("/startSelect.controller/{u}")
	public String processMainAction3() {
		return "work/admin/camp/userSelect";
	}

	// 啟動update
	@GetMapping("/startUpdate.controller/{u}")
	public String processMainAction4() {
		return "work/admin/camp/userUpdate";
	}

	// 啟動mail
	@GetMapping("/startMail.controller/{m}")
	public String processMainAction5() {
		return "work/admin/camp/mailInsert";
	}
	
	// 透過uid搜尋
	@PostMapping("/userSelectUid.controller/{uid}")
	@ResponseBody
	public List<JobBean> processSelectUidAction(@PathVariable UUID uid) {
		List<JobBean> result = jService.findUid(uid);
		return result;
	}

	// 新增
	@PostMapping("/userInsert.controller")
	@ResponseBody
	public List<JobBean> processInsertAction2(@RequestBody JobBean jobBean) {
		UUID uid = jwtService.getUId(request);
		JobBean insert = jService.insert(jobBean, uid);
		List<JobBean> list = Arrays.asList(insert);
		return list;
	}

	// 刪除搜尋是否還有簡歷
	@GetMapping("/userDelete.controller/{rackID}")
	@ResponseBody
	public Boolean processDeleteAction(@PathVariable Integer rackID) {
		return jService.deleteById(rackID);
		 
	}
	// 真刪除
	@DeleteMapping("/userTrueDelete.controller/{rackID}")
	@ResponseBody
	public String processTrueDeleteAction(@PathVariable Integer rackID) {
		return jService.trueDeleteById(rackID);
		
	}

	// 修改
	@PutMapping("/userUpdate.controller/{rackid}")
	@ResponseBody
	public List<JobBean> processUpdateAction(@RequestBody JobBean jBean, @PathVariable Integer rackid) {
		 JobBean updateJob = jService.updateJob(jBean, rackid);
		 List<JobBean> list = Arrays.asList(updateJob);
		return list;
	}

	// 透過rackid找資料後給前端修改
	@PostMapping("/userSelectRackId.controller/{rackID}")
	@ResponseBody
	public JobBean processAction4(@PathVariable Integer rackID) {
		JobBean result = jService.findById(rackID);
		return result;
	}

	// 寄email
	@PostMapping("/userMail.controller")
	@ResponseBody
	public String processAction4(@RequestParam("mail") String toMail,
			@RequestParam("subject") String subject,@RequestParam("remark") String message) {
		mailService.sendEmail(toMail,subject,message );

		return "成功寄出e-mail！！！";
	}
	// 透過uid搜尋camp的東西
	@PostMapping("/selectUUid.controller/{uid}")
	@ResponseBody
	public List<Camp> processSelectUUidAction(@PathVariable UUID uid) {
		List<Camp> result = jService.findUUid(uid);
		
		return result;
	}
	// 透過campid搜尋camp的東西
	@PostMapping("/selectCampid.controller/{campid}")
	@ResponseBody
	public Camp processSelectCampidAction(@PathVariable Integer campid) {
		Camp result = jService.findCampid(campid);
		return result;
	}

}
