package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailAdminWeb;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@Service
public class UserDetailAdminWebDTOMapperUserProfiles implements Function<UserDetailAdminWeb, UserProfiles> {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfiles apply(UserDetailAdminWeb userDetailAdminWeb) {
        UserProfiles userProfiles = userRepository.findById(UUID.fromString(userDetailAdminWeb.uid())).get();
        UserDetail userdetail = userProfiles.getUserdetail();
        UserName usernames = userProfiles.getUsernames();
        UserPrivacy userprivacy = userProfiles.getUserprivacy();
        usernames.setLastname(userDetailAdminWeb.lastname());
        usernames.setFirstname(userDetailAdminWeb.firstname());
        userprivacy.setPhone(userDetailAdminWeb.phone());
        userprivacy.setBirthday(userDetailAdminWeb.birthday());
        userprivacy.setAddress(userDetailAdminWeb.address());
        userdetail.setNickname(userDetailAdminWeb.nickname());
        userdetail.setExp(userDetailAdminWeb.exp());
        userdetail.setLevel(userDetailAdminWeb.level());
        userdetail.setPoint(userDetailAdminWeb.point());
        userdetail.setGender(userDetailAdminWeb.gender());
        userdetail.setSubscribed(userDetailAdminWeb.subscribed());
        userdetail.setShot(userDetailAdminWeb.shot());
        userdetail.setAbout(userDetailAdminWeb.about());
        userProfiles.setRoles(userDetailAdminWeb.roles());
        userProfiles.setUserdetail(userdetail);
        userProfiles.setUsernames(usernames);
        userProfiles.setUserprivacy(userprivacy);
        userProfiles.setAccountnonlocked(userDetailAdminWeb.accountnonlocked());
        userProfiles.setIsenabled(userDetailAdminWeb.isenabled());
        return userProfiles;
    }

}