package com.campingmapping.team4.spring.t411team.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t411team.model.Dao.repository.ApplyRepository;
import com.campingmapping.team4.spring.t411team.model.Dao.repository.InitiatingRepository;
import com.campingmapping.team4.spring.t411team.model.Dao.repository.MessageAreaRepository;
import com.campingmapping.team4.spring.t411team.model.Dao.repository.ThundsupRepository;
import com.campingmapping.team4.spring.t411team.model.entity.Apply;
import com.campingmapping.team4.spring.t411team.model.entity.Initiating;
import com.campingmapping.team4.spring.t411team.model.entity.MessageArea;
import com.campingmapping.team4.spring.t411team.model.entity.Thundsup;

@Service
@Transactional
public class teamService {
	
	@Autowired
	private InitiatingRepository iRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private ThundsupRepository tRepo;
	
	@Autowired
	private MessageAreaRepository mRepo;
	
	@Autowired
	private ApplyRepository aRepo;
	
	//新增Initiating資料
	public Initiating insert(Initiating i ,UUID uid) {
		String information = "";
		
		information += "性別：" + i.getGender() + ";";
			if(i.getLodging() != null) {
				information += "預期住宿：";
				for(int x = 0 ; x < i.getLodging().length ; x++) {
					information += i.getLodging()[x] + ",";
				}
				information = information.substring(0, information.length()-1) + ";";
			}
			if(i.getEquipment() != null) {
				information += "目前裝備：";
				for(int x = 0 ; x < i.getEquipment().length ; x++) {
					information += i.getEquipment()[x] + ",";
				}
				information = information.substring(0, information.length()-1) + ";";
			}
		Date postday = new Date();
		i.setPostdate(postday);
		i.setTag(information);
		i.setUserprofiles(uRepo.findById(uid).get());
		i.setThumbsUp(0);
		i.setViewingCount(0);
		i.setApplycount(0);
		return iRepo.save(i);
	}
	
	//修改Initiating資料
	public Initiating update(Initiating i) {
		String information = "";
		
		information += "性別：" + i.getGender() + ";";
			if(i.getLodging() != null) {
				information += "預期住宿：";
				for(int x = 0 ; x < i.getLodging().length ; x++) {
					information += i.getLodging()[x] + ",";
				}
				information = information.substring(0, information.length()-1) + ";";
			}
			if(i.getEquipment() != null) {
				information += "目前裝備：";
				for(int x = 0 ; x < i.getEquipment().length ; x++) {
					information += i.getEquipment()[x] + ",";
				}
				information = information.substring(0, information.length()-1) + ";";
			}
		i.setTag(information);
		i.setViewingCount(i.getViewingCount());
		i.setThumbsUp(iRepo.findById(i.getInitiatingnum()).get().getThumbsUp());
		return iRepo.save(i);
	}
	
	//跳轉增加瀏覽數
	public Initiating ThumbsUp(Initiating i) {
		return iRepo.save(i);
	}
	
	//刪除Initiating資料
	public void delete(Integer id) {
		iRepo.deleteById(id);
	}
	
	//透過id查詢Initiating資料
	public Initiating findById(Integer id) {
		Optional<Initiating> op = iRepo.findById(id);
		Initiating i = null;
		
		if(op.isPresent()) {
			i = op.get();
		}
		
		return i;
	}
	
	//透過user查詢Initiating資料
	public List<Initiating> findByUser(UserProfiles uid){
		return iRepo.findByUserprofiles(uid);
	}
	
	//查詢Initiating全部資料
	public List<Initiating> findAll(){
		return iRepo.findAll();
	}
	
	//查詢Initiating分頁資料
	public Page<Initiating> findAllpage(Pageable pageable){
	    return iRepo.findAll(pageable);
	}
	
	//動態查詢
	public List<Initiating> selectDynamic(String uid, Date startdate, Date enddate, String camparea){
		List<Initiating> result = new ArrayList<Initiating>();
		List<Initiating> dateList = new ArrayList<Initiating>();
		List<Initiating> campareaList = new ArrayList<Initiating>();
		boolean judge = true;
		
		if (startdate != null && enddate != null) {
			dateList = iRepo.findByStartdateGreaterThanEqualAndEnddateLessThanEqual(startdate, enddate);
			if(dateList.size() == 0) {
				judge = false;
			}
		}else if(startdate != null && enddate == null) {
			dateList = iRepo.findByStartdateGreaterThanEqual(startdate);
		}else if (startdate == null && enddate != null) {
			dateList = iRepo.findByEnddateLessThanEqual(enddate);
		}
		
		if(dateList.size() != 0 && !camparea.equals("0")) {
			campareaList = iRepo.findByCamparea(camparea);
			result.addAll(dateList);
			result.retainAll(campareaList);
		}else if (dateList.size()!= 0 && camparea.equals("0")) {
			result.addAll(dateList);
		}else if(dateList.size() == 0 && !camparea.equals("0") && judge != false){
			campareaList = iRepo.findByCamparea(camparea);
			result.addAll(campareaList);
		}
		
		if(!uid.equals("0") && result.size() != 0) {
			Initiating i = new Initiating();
			i.setUserprofiles(uRepo.findById(UUID.fromString(uid)).get());
			List<Initiating> uList = iRepo.findByUserprofiles(i.getUserprofiles());
			result.retainAll(uList);
		}else if(!uid.equals("0") && result.size() == 0 && judge != false) {
			Initiating i = new Initiating();
			i.setUserprofiles(uRepo.findById(UUID.fromString(uid)).get());
			List<Initiating> uList = iRepo.findByUserprofiles(i.getUserprofiles());
			result.addAll(uList);
		}
		
		return result;
	}
	
	//查詢按讚資料
	public List<Thundsup> selectThundsup(Integer num, UUID u) {
		ArrayList<Thundsup> resultList = new ArrayList<Thundsup>();
		Thundsup t = new Thundsup();
		Initiating i = new Initiating();
		t.setUserprofiles(uRepo.findById(u).get());
		i.setInitiatingnum(num);
		t.setInitiating(i);
		List<Thundsup> tList = tRepo.findByUserprofiles(t.getUserprofiles());
		List<Thundsup> iList = tRepo.findByInitiating(i);
		for (Thundsup thundsup1 : iList) {
			for (Thundsup thundsup2 : tList) {
				if (thundsup1.equals(thundsup2)) {
					resultList.add(thundsup2);
					return resultList;
				}
			}
		}
		return null;
	}
	
	
	//新增按讚資料
	public Thundsup insertThundsup(Integer num, UUID u) {
		
		Thundsup t = new Thundsup();
		Initiating i = new Initiating();
		
		t.setUserprofiles(uRepo.findById(u).get());
		i.setInitiatingnum(num);
		t.setUserprofiles(uRepo.findById(u).get());
		t.setInitiating(i);
		
		return tRepo.save(t);
	}
	
	//刪除按讚資料
	public void deleteThundsup(Integer tid) {
		tRepo.deleteById(tid);
	}
	
	//透過id查詢是否按讚
	public List<Thundsup> findThumbsUpByid(UUID uid){
		return tRepo.findByUserprofiles(uRepo.findById(uid).get());
	}
	
	//透過InitiatingID查詢留言
	public List<MessageArea> selectByMid(Integer iid){
		Initiating i = new Initiating();
		i.setInitiatingnum(iid);
		return mRepo.findByInitiating(i);
	}
	
	//新增留言
	public MessageArea insertMessasge(UUID uid, Integer num, String message){
		MessageArea m = new MessageArea();
		m.setInitiating(iRepo.findById(num).get());
		m.setUserprofiles(uRepo.findById(uid).get());
		m.setMessage(message);
		m.setPostday(new Date());
		return mRepo.save(m);
	}
	
	//刪除留言
	public void deleteMessage(Integer mid) {
		mRepo.deleteById(mid);
	}
	
	//修改留言
	public MessageArea updateMessage(Integer mid, String message) {
		MessageArea m = mRepo.findById(mid).get();
		m.setMessage(message);
		return mRepo.save(m);
	}
	
	//查詢登入用戶需回覆的申請表單
	public List<Apply> findApplyByUser(UUID uid){
		ArrayList<Apply> resultList = new ArrayList<Apply>();
		List<Initiating> iList = iRepo.findByUserprofiles(uRepo.findById(uid).get());
		if(iList.size() != 0) {
			for (Initiating initiating : iList) {
				List<Apply> aList = aRepo.findByInitiatingAndResponsestate(initiating, 0);
				if(aList.size() != 0) {
					resultList.addAll(aList);
				}
			}
			if(resultList != null) {
				return resultList;
			}
		}
		return null;
	}
	
	//查詢登入用戶需要查看的申請回覆
	public List<Apply> findApplyResponseByUser(UUID uid) {
		return aRepo.findByUserprofilesAndResponsestate(uRepo.findById(uid).get(), 1);
	}
	
	//透過用戶及表單號碼查詢申請表單是否已經存在
	public List<Apply> findByUserAndInitiatingnum(UUID uid, Apply a){
		return aRepo.findByInitiatingAndUserprofiles(iRepo.findById(a.getInitiatingnumber()).get(), uRepo.findById(uid).get());
	}
	
	//新增申請表單
	public Apply insertApply(UUID uid, Apply a) {
		a.setUserprofiles(uRepo.findById(uid).get());
		a.setInitiating(iRepo.findById(a.getInitiatingnumber()).get());
		a.setResponsestate(0);
		a.setPairstate(0);
		a.setLimit(0);
		a.setResponsemessage(" ");
		return aRepo.save(a);
	}
	
	//修改申請表單
	public Apply updateApply(Apply a) {
		return aRepo.save(a);
	}
	
	//透過aid查詢申請表單
	public Apply findByAid(Integer aid){
		Optional<Apply> ap = aRepo.findById(aid);
		Apply a = null;
		if(ap.isPresent()) {
			a = ap.get();
		}
		return a;
	}
	
	//接受申請表單
	public Apply acceptApply(Apply a) {
		a.setPairstate(1);
		a.setResponsestate(a.getResponsestate()+1);
		Initiating i = iRepo.findById(a.getInitiating().getInitiatingnum()).get();
		i.setAcceptablenum(i.getAcceptablenum() - a.getApplypeople());
		i.setCurrentnum(i.getCurrentnum() + a.getApplypeople());
		iRepo.save(i);
		return a;
	}
	
	//拒絕申請表單
	public Apply rejectApply(Apply a) {
		a.setLimit(a.getLimit()+1);
		a.setResponsestate(a.getResponsestate()+1);
		return a;
	}
	
	//永久拒絕特定用戶申請特定表單
	public Apply permanentrejectApply(Apply a) {
		a.setLimit(3);
		a.setResponsestate(a.getResponsestate()+1);
		return a;
	}
	
}
