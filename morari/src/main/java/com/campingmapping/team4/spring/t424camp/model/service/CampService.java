package com.campingmapping.team4.spring.t424camp.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t424camp.model.dao.repository.CampRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.City;
import com.campingmapping.team4.spring.t424camp.model.entity.Order;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;
import com.campingmapping.team4.spring.t424camp.model.entity.Tag;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class CampService {

	@Autowired
	private CampRepository campRepository;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CityService cityService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	

	// 推薦Camps
	public List<Camp> recommendCampToUser(UUID uid) {
		// user
		UserProfiles user = userRepository.findById(uid).get();
		// orders of user
		Object[] orders = user.getCampOrder().toArray();

		if(orders.length == 0){
			return null;
		}

		int ordersMax = orders.length - 1;
		int ordersMin = 0;
		int ranOrdersNum = ordersMin + (int) (Math.random() * (ordersMax - ordersMin + 1));
		// ranOrder
		Order ranOrder = (Order) orders[ranOrdersNum];
		// cityId of ranOrder
		Integer ranCityID = ranOrder.getCamp().getCity().getCityID();
		// camps of city
		boolean flag;
		List<Camp> campList = campRepository.findByCityId(ranCityID);

		for (int j = 0; j < campList.size(); j++) {
			flag = false;

			for (int i = 0; i < orders.length; i++) {
				if (campList.get(j).getCampID() == ((Order) orders[i]).getCamp().getCampID()) {
					flag = true;
					break;
				}
			}
			if (flag) {
				campList.remove(campList.get(j));
			}

		}

		List<Camp> resultList = new ArrayList<Camp>();
		if (campList.size() > 3) {
			resultList = new ArrayList<Camp>();
			Random rand = new Random();
			HashSet<Integer> set = new HashSet<>();
			while (set.size() < 3) {
				int num = rand.nextInt(campList.size());
				set.add(num);
			}
			for (int i = 0; i < set.size(); i++) {
				Integer index = (Integer) set.toArray()[i];
				resultList.add(campList.get(index));
			}
			return resultList;
		}

		return campList;
	}

	// 新增Camp
	public Camp insert(String campName, Integer cityID, String location, String campPicturesPath, String description,
			int[] tagIDs) {

		Set<Tag> tags = new HashSet<Tag>();
		for (int tagID : tagIDs) {
			Tag tag = tagService.findById(tagID);
			tags.add(tag);
		}

		City city = cityService.findById(cityID);
		
		//使用者
		UUID uid = jwtService.getUId(httpServletRequest);
		UserProfiles user = userRepository.findById(uid).get();

		Camp camp = new Camp(campName, city, location, campPicturesPath, description, tags, user);

		return campRepository.save(camp);
	}
	
	//查詢全部訂單, 分頁, 順序
	public Page<Camp> getByPage(Pageable pageable) {
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("campID"));
		Page<Camp> findAll = campRepository.findAll(sortedPageable);
		
		return findAll;
	}

	// 找全部Camp
	public List<Camp> findAll() {
		return campRepository.findAll(Sort.by("campID"));
	}

	// Id找Camp
	public Camp findById(int campId) {
		Optional<Camp> op = campRepository.findById(campId);
		Camp camp = null;

		if (op.isPresent()) {
			camp = op.get();
		}

		return camp;
	}

	// 更新Camp
	public Camp update(Integer campID, String campName, Integer cityID, String location, String campPicturesPath,
			String description, int[] tagIDs) {

		Set<Tag> tags = new HashSet<Tag>();
		for (int tagID : tagIDs) {
			Tag tag = tagService.findById(tagID);
			tags.add(tag);
		}

		City city = cityService.findById(cityID);
		
		//使用者
		UUID uid = jwtService.getUId(httpServletRequest);
		UserProfiles user = userRepository.findById(uid).get();

		Camp camp = new Camp(campID, campName, city, location, campPicturesPath, description, tags, user);

		return campRepository.save(camp);
	}

	// 刪除Camp
	public boolean deleteById(Integer campId) {
		Camp camp = findById(campId);

		if (camp != null) {
			// //fkCityId設null
			// camp.setCity(null);
			//
			// //刪除TagOfCamp
			// Set<Tag> tags = camp.getTags();
			// if(tags.size() != 0) {
			// Iterator<Tag> it1 = tags.iterator();
			// while (it1.hasNext()) {
			// it1.remove();
			// }
			// }
			//
			// 刪除SiteOfCamp
			Set<Site> sites = camp.getSites();
			if (sites.size() != 0) {
				for (Site site : sites) {
					site.setCamp(null);
					siteService.deleteById(site.getSiteID());
				}
			}
		}

		campRepository.deleteById(campId);
		return true;
	}

}
