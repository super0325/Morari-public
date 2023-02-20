package com.campingmapping.team4.spring.t424camp.model.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t424camp.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	 Optional<Tag> findByTagName(String tagName);
	
}
