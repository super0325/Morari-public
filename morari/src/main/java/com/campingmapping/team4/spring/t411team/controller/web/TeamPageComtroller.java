package com.campingmapping.team4.spring.t411team.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
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
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.t411team.model.entity.Apply;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;
import com.campingmapping.team4.spring.t411team.model.entity.MessageArea;
import com.campingmapping.team4.spring.t411team.model.entity.Thundsup;
import com.campingmapping.team4.spring.t411team.model.service.teamService;
import com.campingmapping.team4.spring.utils.config.GoogleFileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/team")
public class TeamPageComtroller {
	
	@GetMapping({ "", "/" })
	public String teamIndex() {
		return "team/guest/index";
	}
	
	@Autowired
	private teamService teamService;
	
	@RequestMapping("/teammanager.controller")
	public String  processmainAction() {
		return "team/admin/teammanager";
	}
	
	//新增跳轉
	@RequestMapping("/insert.controller")
	public String intoInsertAction() {
		return "team/admin/insertManager";
	}
	
	@GetMapping("/view.controller")
	@ResponseBody
	public List<Initiating> showAll() throws JsonProcessingException{
		List<Initiating> view = teamService.findAll();
		return view;
	}
	
	//分頁顯示
	@GetMapping("/viewPaging.controller/{page}")
	@ResponseBody
	public List<Initiating> showAllpage(@PathVariable("page")@Nullable String page) throws JsonProcessingException{
		int pagenum = Integer.valueOf(page.substring(1,page.length()-1));
		
		Pageable pageable = PageRequest.of(pagenum - 1, 5);
		Page<Initiating> view = teamService.findAllpage(pageable);
		return view.getContent();
	}
	
	//修改Initiating
	@PostMapping("/insertMaterial.controller/{uid}")
	@ResponseBody
	public String insert(@RequestBody Initiating i , @PathVariable UUID uid) {
		teamService.insert(i, uid);
		return "Insert OK";
	}
	
	//刪除Initiating
	@DeleteMapping("/delete.controller/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		String initiatingnum = id.substring(1,id.length()-1);
		teamService.delete(Integer.valueOf(initiatingnum));
		return "Delete OK";
	}
	
	//管理者修改
	@GetMapping("/update.controller")
	public String display() {
		return "team/admin/updateManager";
	}
	
	//使用者修改
	@GetMapping("/guestupdate.controller")
	public String guestdisplay() {
		return "team/guest/guestupdate";
	}
	
	
	@PutMapping("/updateMaterial.controller")
	@ResponseBody
	public String update(@RequestBody Initiating i) {
		Initiating in = teamService.findById(i.getInitiatingnum());
		i.setPostdate(in.getPostdate());
		i.setUserprofiles(in.getUserprofiles());
		teamService.update(i);
		return "update OK";
	}
	
	//動態查詢
	@PostMapping("/select.controller/{uid}")
	@ResponseBody
	public List<Initiating> select(@RequestBody Initiating i, @PathVariable String uid){
		List<Initiating> result = new ArrayList<>();
		
		if(i.getInitiatingnum()!=0) {
			Initiating uidResult = teamService.findById(i.getInitiatingnum());
			result.add(uidResult);
		}else {
			String id = uid.substring(1, uid.length()-1);
			List<Initiating> selectResult = teamService.selectDynamic(id, i.getStartdate(), i.getEnddate(), i.getCamparea());
			result.addAll(selectResult);
		}
		return result;
	}
	
	//透過id找尋initiating資料
	@PostMapping("/findById.controller/{id}")
	@ResponseBody
	public Initiating findById(@PathVariable("id") String id){
		String initiatingnum = id.substring(1,id.length()-1);
		Initiating result = teamService.findById(Integer.valueOf(initiatingnum));
		return result;
	}
	
	@PostMapping("/thumbsUp.controller/{uid}")
	@ResponseBody
	public String thumbsUp(@RequestBody @Nullable String[] arr,@PathVariable UUID uid) {
		int x;
		for(int i = 0; i < arr.length; i++) {
			x = Integer.valueOf(arr[i]);
			if (x < 0) {
				x = 0-x;
				List<Thundsup> selectresult = teamService.selectThundsup(x, uid);
				if(selectresult != null) {
					for (Thundsup thundsup : selectresult) {
						Integer tid = thundsup.getThundsupid();
						teamService.deleteThundsup(tid);
						Initiating initiating = teamService.findById(x);
						initiating.setThumbsUp(initiating.getThumbsUp()-1);
						teamService.update(initiating);
					}
				}
			}else {
				List<Thundsup> selectresult = teamService.selectThundsup(x, uid);
				if (selectresult == null) {
					teamService.insertThundsup(x, uid);
					Initiating initiating = teamService.findById(x);
					initiating.setThumbsUp(initiating.getThumbsUp()+1);
					teamService.update(initiating);
				}
			}
			
		}
		
		return "ok";
	}
	
	//查詢案讚數
	@GetMapping("/selectThumbsUp.controller/{uid}")
	@ResponseBody
	public List<Thundsup> findThumbsUpByUser(@PathVariable UUID uid){
		return teamService.findThumbsUpByid(uid);
	}
	
	//更多資訊跳轉
	@GetMapping("/moreInformation.controller/{num}")
	public String moreinformation(@PathVariable Integer num) {
		Initiating i = teamService.findById(num);
		int viewingCount1 = i.getViewingCount();
		i.setViewingCount(viewingCount1+1);
		teamService.ThumbsUp(i);
		return "team/guest/initiatingform";
	}
	
	//可以用來當user前端跳轉，可調整
	@GetMapping("/apply.controller")
	public String apply() {
		return "team/guest/apply2";
	}
	
	@GetMapping("/guestinsert.controller")
	public String guestinsert() {
		return "team/guest/guestinsert";
	}
	
	//顯示本人留言
	@GetMapping("/selectMessage.controller/{num}")
	@ResponseBody
	public List<MessageArea> selectMessage(@PathVariable String num){
		Integer mid = Integer.valueOf(num.substring(1,num.length()-1));
		return teamService.selectByMid(mid);
	}
	
	//新增留言
	@PostMapping("/insertMessage.controller/{uid}")
	@ResponseBody
	public List<MessageArea> insertMessage(@RequestBody String[] messageArr,@PathVariable UUID uid){
		teamService.insertMessasge(uid, Integer.valueOf(messageArr[0]), messageArr[1]);
		return null;
	}
	
	//刪除留言
	@PostMapping("/deleteMessage.controller/{mid}")
	@ResponseBody
	public String deleteMessage(@PathVariable Integer mid) {
		teamService.deleteMessage(mid);
		return "delete OK";
	}
	
	//新增申請表單
	@PostMapping("/insertApply.controller/{uid}")
	@ResponseBody
	public String insertApply(@RequestBody Apply a,@PathVariable UUID uid) {
		List<Apply> aList = teamService.findByUserAndInitiatingnum(uid, a);
		if(aList != null) {
			for (Apply apply : aList) {
				Apply a2 = teamService.findByAid(apply.getApplyid());
				a2.setApplypeople(a.getApplypeople());
				a2.setApplymessage(a.getApplymessage());
				a2.setResponsestate(0);
				a2.setLimit(a2.getLimit()+1);
				teamService.updateApply(a2);
				return "apply OK";
			}
		}
		teamService.insertApply(uid, a);
		return "apply OK";
	}
	
	//查詢需要回覆的申請清單
	@GetMapping("/findApply.controller/{uid}")
	@ResponseBody
	public List<Apply> findApplyByUser(@PathVariable UUID uid){
		return teamService.findApplyByUser(uid);
	}
	
	//查詢收到回覆的申請清單
	@GetMapping("/findApplyResponse.controller/{uid}")
	@ResponseBody
	public List<Apply> findApplyResponse(@PathVariable UUID uid){
		return teamService.findApplyResponseByUser(uid);
	}
	
	//查看了收到回覆的申請清單
	@PostMapping("overViewApplyResponse.controller/{uid}")
	@ResponseBody
	public String overViewApplyResponse(@PathVariable UUID uid) {
		List<Apply> aList = teamService.findApplyResponseByUser(uid);
		for (Apply apply : aList) {
			Apply a = teamService.findByAid(apply.getApplyid());
			a.setResponsestate(a.getResponsestate()+1);
			teamService.updateApply(a);
		}
		return "view Over";
	}
	
	//接受申請的表單
	@PostMapping("acceptApply.controller/{aid}")
	@ResponseBody
	public String acceptApply(@PathVariable String aid) {
		Apply a = teamService.findByAid(Integer.valueOf(aid));
		teamService.acceptApply(a);
		return "accept OK";
	}
	
	//拒絕申請的表單
	@PostMapping("/reject.controller/{aid}")
	@ResponseBody
	public String rejectApply(@PathVariable String aid) {
		Apply a = teamService.findByAid(Integer.valueOf(aid));
		teamService.rejectApply(a);
		return "reject OK";
	}
	
	//永久拒絕申請的表單
	@PostMapping("/permanentreject.controller/{aid}")
	@ResponseBody
	public String permanentrejectApply(@PathVariable String aid) {
		Apply a = teamService.findByAid(Integer.valueOf(aid));
		teamService.permanentrejectApply(a);
		return "permanentreject OK";
	}
	
	//查看自己是否可以發出申請
	@PostMapping("/findApplyByUserAndInitiatingnum.controller/{uid}/{num}")
	@ResponseBody
	public List<Apply> findApplyByUserAndInitiatingnum(@PathVariable UUID uid,@PathVariable String num){
		Apply a = new Apply();
		a.setInitiatingnumber(Integer.valueOf(num));
		return teamService.findByUserAndInitiatingnum(uid, a);
	}
	
	//回傳img的google位址
	@PostMapping("/uploadpicturetogoogle.controller")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName="pd"+UUID.randomUUID().toString();
		return GoogleFileUtil.uploadFile(fileName,file);
	}

}
