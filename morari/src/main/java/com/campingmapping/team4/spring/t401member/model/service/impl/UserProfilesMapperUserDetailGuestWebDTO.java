package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestWeb;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@Service
public class UserProfilesMapperUserDetailGuestWebDTO implements Function<UserProfiles, UserDetailGuestWeb> {

    @Override
    public UserDetailGuestWeb apply(UserProfiles userProfiles) {
        UserDetail userDetail = userProfiles.getUserdetail();
        return new UserDetailGuestWeb(
                userProfiles.getUid().toString(),
                userDetail.getNickname(),
                userDetail.getExp(),
                userDetail.getLevel(),
                userDetail.getPoint(),
                userDetail.getShot(),
                userDetail.getAbout());

    }

}