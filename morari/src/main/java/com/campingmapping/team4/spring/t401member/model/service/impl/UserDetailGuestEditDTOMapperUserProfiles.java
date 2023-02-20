package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@Service
public class UserDetailGuestEditDTOMapperUserProfiles implements Function<UserDetailGuestEdit, UserProfiles> {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfiles apply(UserDetailGuestEdit userDetailGuestEdit) {
        UserProfiles userProfiles = userRepository.findById(UUID.fromString(userDetailGuestEdit.uid())).get();
        UserDetail userdetail = userProfiles.getUserdetail();
        UserName usernames = userProfiles.getUsernames();
        UserPrivacy userprivacy = userProfiles.getUserprivacy();
        usernames.setLastname(userDetailGuestEdit.lastname());
        usernames.setFirstname(userDetailGuestEdit.firstname());
        userprivacy.setPhone(userDetailGuestEdit.phone());
        userprivacy.setBirthday(userDetailGuestEdit.birthday());
        userprivacy.setAddress(userDetailGuestEdit.address());
        userdetail.setNickname(userDetailGuestEdit.nickname());
        userdetail.setGender(userDetailGuestEdit.gender());
        userdetail.setSubscribed(userDetailGuestEdit.subscribed());
        userdetail.setShot(userDetailGuestEdit.shot());
        userdetail.setAbout(userDetailGuestEdit.about());
        userProfiles.setUserdetail(userdetail);
        userProfiles.setUsernames(usernames);
        userProfiles.setUserprivacy(userprivacy);
        return userProfiles;
    }

}