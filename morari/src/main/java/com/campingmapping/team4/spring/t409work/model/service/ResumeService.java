package com.campingmapping.team4.spring.t409work.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t409work.model.Dao.repository.JobRepository;
import com.campingmapping.team4.spring.t409work.model.Dao.repository.ResumeRepository;
import com.campingmapping.team4.spring.t409work.model.entity.ResumeBean;

@Service
@Transactional
public class ResumeService {

	@Autowired
	private ResumeRepository reDao;

	@Autowired
	private UserRepository uDao;

	@Autowired
	private JobRepository jDao;

	// 秀全部
	public List<ResumeBean> findAll() {
		List<ResumeBean> selectAll = reDao.findAll();
		return selectAll;
	}

	// 新增履歷
	public ResumeBean insert(ResumeBean rBean, UUID u) {
		rBean.setUserprofiles(uDao.findById(u).get());
		return reDao.save(rBean);
	}

	// 刪除履歷
	public void deleteById(int number) {
		reDao.deleteById(number);
	}

	// 改履歷內容
	public ResumeBean updateJob(ResumeBean rBean, Integer number) {
		Optional<ResumeBean> result = reDao.findById(number);
		if (result.isPresent()) {
			ResumeBean resumeBean = result.get();
			// 將前端傳進來的rBean的值複製到resumeBean
			resumeBean.setName(rBean.getName());
			resumeBean.setAge(rBean.getAge());
			resumeBean.setGender(rBean.getGender());
			resumeBean.setMail(rBean.getMail());
			resumeBean.setPhone(rBean.getPhone());
			resumeBean.setEducational(rBean.getEducational());
			resumeBean.setSkill(rBean.getSkill());
//	        jBean.setUserprofiles(result.get().getUserprofiles().getUid());	        
			return reDao.save(resumeBean);
		}
		// 找不到對應的資料
		return null;
	}

	// 透過number抓一筆資料(有需要嗎?)
	public ResumeBean findById(Integer number) {
		Optional<ResumeBean> result = reDao.findById(number);
		ResumeBean r = null;
		if (result.isPresent()) {
			r = result.get();
		}
		return r;
	}

	// 透過會員id找履歷
	public ResumeBean findByUid(UUID uid) {
		try {
			Optional<UserProfiles> user = uDao.findById(uid);
			if (user.isPresent()) {
				ResumeBean result = user.get().getResume();
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// 存job到resumes裡
	public ResumeBean save(ResumeBean resume) {
		return reDao.save(resume);
	}

	// 透過rackid找應徵的履歷
	public Collection<ResumeBean> findRid(Integer rackid) {
		Collection<ResumeBean> resumes = jDao.findById(rackid).get().getResumes();
		return resumes;
	}
}
