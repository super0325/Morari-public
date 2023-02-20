package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailAdminWeb;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@Service
public class UserProfilesMapperUesrDetailAdminWebDTO implements Function<UserProfiles, UserDetailAdminWeb> {

    @Override
    public UserDetailAdminWeb apply(UserProfiles userProfiles) {
        UserName userName = userProfiles.getUsernames();
        UserDetail userDetail = userProfiles.getUserdetail();
        UserPrivacy userPrivacy = userProfiles.getUserprivacy();
        return new UserDetailAdminWeb(
                userProfiles.getUid().toString(),
                userDetail.getNickname(),
                userName.getFirstname(),
                userName.getLastname(),
                userProfiles.getEmail(),
                userPrivacy.getPhone(),
                userProfiles.getRoles(),
                userPrivacy.getBirthday(),
                userPrivacy.getAddress(),
                userDetail.getGender(),
                userDetail.getExp(),
                userDetail.getLevel(),
                userDetail.getPoint(),
                userDetail.getRegisterdata(),
                userDetail.getSubscribed(),
                userDetail.getShot(),
                userDetail.getAbout(),
                userProfiles.getIsenabled(),
                userProfiles.getAccountnonlocked());

    }

}