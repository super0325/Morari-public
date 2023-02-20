package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;

@Service
public class UserProfilesMapperUesrDetailGuestEditDTO implements Function<UserProfiles, UserDetailGuestEdit> {

    @Override
    public UserDetailGuestEdit apply(UserProfiles userProfiles) {
        UserName userName = userProfiles.getUsernames();
        UserDetail userDetail = userProfiles.getUserdetail();
        UserPrivacy userPrivacy = userProfiles.getUserprivacy();
        return new UserDetailGuestEdit(
                userProfiles.getUid().toString(),
                userDetail.getNickname(),
                userName.getFirstname(),
                userName.getLastname(),
                userProfiles.getEmail(),
                userPrivacy.getPhone(),
                userPrivacy.getBirthday(),
                userPrivacy.getAddress(),
                userDetail.getGender(),
                userDetail.getExp(),
                userDetail.getLevel(),
                userDetail.getPoint(),
                userDetail.getSubscribed(),
                userDetail.getShot(),
                userDetail.getAbout());

    }

}