package com.campingmapping.team4.spring.t409work.controller.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t409work.model.entity.JobBean;
import com.campingmapping.team4.spring.t409work.model.entity.ResumeBean;
import com.campingmapping.team4.spring.t409work.model.service.JobService;
import com.campingmapping.team4.spring.t409work.model.service.ResumeService;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

// job的前台+resume的前台
@Controller
@RequestMapping("/guest/work")
public class GuestworkController {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ResumeService rService;

	@Autowired
	private JobService jService;

	@Autowired
	private UserRepository userRepository;

	// 啟動我的首頁
	@GetMapping("/workGuest.controller")
	public String processMainAction1() {
		return "work/guest/workGuest";
	}
	
	// 啟動後臺簡歷
	@GetMapping("/resume.controller")
	public String processMainAction6() {
		return "camper/admin/resume";
	}

	// 啟動insert
	@GetMapping("/startResumeInsert.controller")
	public String processMainAction2() {
		return "camper/admin/resumeInsert";
	}

	// 啟動select
	@PostMapping("/resumeSelect.controller")
	public String processMainAction3() {
		return "work/guest/resumeSelect";
	}

	// 啟動update
	@PostMapping("/update.controller")
	public String processMainAction4() {
		return "camper/admin/resumeUpdate";
	}

	// 啟動workDetail
	@GetMapping("/workDetail.controller/{rackid}")
	public String processMainAction5() {
		return "work/guest/workDetail";
	}

	// 新增履歷
	@PostMapping("/resumeInsert.controller")
	@ResponseBody
	public String processInsertAction2(@RequestBody ResumeBean rBean) {
		UUID uid = jwtService.getUId(request);
		rService.insert(rBean, uid);
		return "新增成功！";
	}

	// 修改履歷 後台
	@PutMapping("/resumeUpdate.controller/{number}")
	@ResponseBody
	public String processUpdateAction(@RequestBody ResumeBean rBean, @PathVariable Integer number) {
		 rService.updateJob(rBean, number);
		 return "修改完成！";
	}
	

	// 透過rackid找資料後給前端修改
	@PostMapping("/guestSelectRackId.controller/{rackID}")
	@ResponseBody
	public JobBean processAction4(@PathVariable Integer rackID) {
		JobBean result = jService.findById(rackID);
		return result;
	}

	// 模糊搜尋職缺
	@PostMapping("/guestSelectLike.controller/{job}")
	@ResponseBody
	public List<JobBean> processSelectlikeAction(@PathVariable String job) {
		List<JobBean> result = jService.findByJobisLike(job);
		return result;
	}
	// 模糊搜尋類型
	@PostMapping("/selectTypeLike.controller/{type}")
	@ResponseBody
	public List<JobBean> processSelectlikeTypeAction(@PathVariable String type) {
		List<JobBean> result = jService.findByTypeisLike(type);
		return result;
	}

	// 透過uid找履歷
	@GetMapping("/guestSelectResume.controller/{uid}")
	@ResponseBody
	public ResumeBean processSelectUidAction(@PathVariable UUID uid) {
		ResumeBean result = rService.findByUid(uid);
		return result;
	}

	// 找全部
	@PostMapping("/jobShowAll.controller")
	@ResponseBody
	public List<JobBean> processShowJobAllAction() {
		List<JobBean> result = jService.findAll();
		return result;
	}

	// 職缺應徵投履歷
	@PostMapping("/applyJob.controller/{uid}/{rackid}")
	@ResponseBody
	public boolean applyJob(@PathVariable("uid") UUID uid, @PathVariable("rackid") Integer rackid) {
		try {
			// 通過uid找到履歷
			Optional<UserProfiles> findById = userRepository.findById(uid);
			if (!findById.isPresent()) {
			return false;
			}
			ResumeBean resume = findById.get().getResume();
			if (resume == null) {
			return false;
			}
		    // 通過rackid找到職缺
		    JobBean job = jService.findById(rackid);
		    if (job == null) {
		        return false;
		    }
		    // 將職缺加入履歷的jobs中
		    resume.getJobs().add(job);
		    // 將履歷加入職缺的resumes中
		    job.getResumes().add(resume);
		    // 儲存履歷和職缺
		    rService.save(resume);
		    jService.save(job);
		    return true;
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
	}

}
