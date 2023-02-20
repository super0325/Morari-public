package com.campingmapping.team4.spring.t401member.model.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.t401member.model.dto.UserDetailAdminWeb;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestWeb;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    public List<UserDetailAdminWeb> showAllUser();

    public String getShot(HttpServletRequest request);

    public String getNickname(HttpServletRequest request);

    public Boolean adminUpdateUser(UserDetailAdminWeb userDetailAdminWeb);

    public Boolean updateaccountlocked(UUID uid, Boolean accountnonlocked);

    public Boolean updateenabled(UUID uid, Boolean isenabled);

    public UserDetailGuestWeb getUserDetail(UUID uid);

    public UserDetailGuestEdit getUesrDetailGuestEdit(HttpServletRequest request);

    public Boolean guestUpdateUser(UserDetailGuestEdit userDetailGuestEdit);

    public List<Date> getUserRegisterDate();

    public Collection<? extends GrantedAuthority> getUserRoles(HttpServletRequest request);

    public String updateShot(String id,MultipartFile file);
}
