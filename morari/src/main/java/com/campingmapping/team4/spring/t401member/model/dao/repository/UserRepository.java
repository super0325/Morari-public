package com.campingmapping.team4.spring.t401member.model.dao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

public interface UserRepository extends JpaRepository<UserProfiles, UUID> {
  Optional<UserProfiles> findByEmail(String email);
  
}