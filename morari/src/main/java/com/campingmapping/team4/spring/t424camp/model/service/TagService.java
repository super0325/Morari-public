package com.campingmapping.team4.spring.t424camp.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campingmapping.team4.spring.t424camp.model.dao.repository.TagRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Tag;

@Service
@Transactional
public class TagService {

	@Autowired
	private TagRepository tRepo;

	// 找全部Tag
	public List<Tag> findAll() {
		return tRepo.findAll();
	}

	// Id找Tag
	public Tag findById(Integer tagId) {
		Optional<Tag> op = tRepo.findById(tagId);
		Tag tag = null;

		if (op.isPresent()) {
			tag = op.get();
		}

		return tag;
	}

}
