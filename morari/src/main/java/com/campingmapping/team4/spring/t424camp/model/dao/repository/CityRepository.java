package com.campingmapping.team4.spring.t424camp.model.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t424camp.model.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	Optional<City> findByCityName(String cityName);

}
