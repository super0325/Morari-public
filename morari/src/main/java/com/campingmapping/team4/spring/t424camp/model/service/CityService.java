package com.campingmapping.team4.spring.t424camp.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t424camp.model.dao.repository.CityRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.City;

@Service
@Transactional
public class CityService {

	@Autowired
	private CityRepository cRepo;

	// 找全部City
	public List<City> findAll() {
		return cRepo.findAll();
	}

	// Id找City
	public City findById(Integer cityId) {
		Optional<City> op = cRepo.findById(cityId);
		City city = null;

		if (op.isPresent()) {
			city = op.get();
		}

		return city;
	}

	// cityIds搜Camps
	public List<Camp> findCampsByCityIds(int[] cityIDs) {
		List<Camp> camps = new ArrayList<Camp>();

		for (int cityID : cityIDs) {
			City city = findById(cityID);
			camps.addAll(city.getCamps());
		}

		return camps;
	}

}
