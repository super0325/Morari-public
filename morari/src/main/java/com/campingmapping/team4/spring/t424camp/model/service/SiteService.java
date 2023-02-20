package com.campingmapping.team4.spring.t424camp.model.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t424camp.model.dao.repository.SiteRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;

@Service
@Transactional
public class SiteService {

	@Autowired
	private SiteRepository sRepo;

	@Autowired
	private CampService campService;

	// 新增Site
	public Site insert(String siteName, String sitePicturesPath, Integer totalSites, Integer siteMoney, int campID) {
		Camp camp = campService.findById(campID);

		Site site = new Site(siteName, sitePicturesPath, totalSites, siteMoney, camp);

		return sRepo.save(site);
	}

	// CampId找Site
	public Set<Site> findSiteByCampId(int campId) {
		Camp camp = campService.findById(campId);

		return camp.getSites();
	}

	// SiteId找Site
	public Site findBySiteId(int siteId) {
		Optional<Site> op = sRepo.findById(siteId);
		Site site = null;

		if (op.isPresent()) {
			site = op.get();
		}

		return site;
	}

	// 更新Site
	public Site update(int siteID, String siteName, String sitePicturesPath, Integer totalSites, Integer siteMoney,
			int campId) {
		Camp camp = campService.findById(campId);

		Site site = new Site(siteID, siteName, sitePicturesPath, totalSites, siteMoney, camp);

		return sRepo.save(site);
	}

	// 刪除Site
	public void deleteById(Integer siteId) {
		sRepo.deleteById(siteId);
	}

}
