package com.campingmapping.team4.spring.t401member.model.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailAdminWeb;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestWeb;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.t401member.model.service.UserService;
import com.campingmapping.team4.spring.utils.config.GoogleFileUtil;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserProfilesMapperUesrDetailAdminWebDTO userpProfilesMapperUesrDetailAdminWebDTO;
    @Autowired
    UserDetailAdminWebDTOMapperUserProfiles userDetailAdminWebDTOMapperUserProfiles;
    @Autowired
    UserProfilesMapperUserDetailGuestWebDTO userProfilesMapperUserDetailguestWebDTO;
    @Autowired
    UserProfilesMapperUesrDetailGuestEditDTO userProfilesMapperUserDetailGuestEditDTO;
    @Autowired
    UserDetailGuestEditDTOMapperUserProfiles userDetailGuestEditDTOMapperUserProfiles;

    @Override
    @Transactional
    public List<UserDetailAdminWeb> showAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userpProfilesMapperUesrDetailAdminWebDTO)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public String getShot(HttpServletRequest request) {
        UUID uid = jwtService.getUId(request);
        return userRepository.findById(uid).get().getUserdetail().getShot();
    }

    @Override
    @Transactional
    public String getNickname(HttpServletRequest request) {
        UUID uid = jwtService.getUId(request);
        return userRepository.findById(uid).get().getUserdetail().getNickname();
    }

    @Override
    @Transactional
    public Boolean adminUpdateUser(UserDetailAdminWeb user) {
        try {
            userRepository.save(userDetailAdminWebDTOMapperUserProfiles.apply(user));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    @Transactional
    public Boolean updateaccountlocked(UUID uid, Boolean accountnonlocked) {
        try {
            UserProfiles userProfiles = userRepository.findById(uid).get();
            userProfiles.setAccountnonlocked(accountnonlocked);
            userRepository.save(userProfiles);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updateenabled(UUID uid, Boolean isenabled) {
        try {
            UserProfiles userProfiles = userRepository.findById(uid).get();
            userProfiles.setIsenabled(isenabled);
            userRepository.save(userProfiles);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public UserDetailGuestWeb getUserDetail(UUID uid) {
        return userProfilesMapperUserDetailguestWebDTO.apply(userRepository.findById(uid).get());
    }

    @Override
    @Transactional
    public UserDetailGuestEdit getUesrDetailGuestEdit(HttpServletRequest request) {
        UUID uid = jwtService.getUId(request);
        UserProfiles userProfiles = userRepository.findById(uid).get();
        UserDetailGuestEdit uesrDetailGuestEdit = userProfilesMapperUserDetailGuestEditDTO.apply(userProfiles);
        return uesrDetailGuestEdit;
    }

    @Override
    @Transactional
    public Boolean guestUpdateUser(UserDetailGuestEdit user) {
        try {
            userRepository.save(userDetailGuestEditDTOMapperUserProfiles.apply(user));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Date> getUserRegisterDate() {
        return userRepository.findAll().stream().map(UserProfiles -> UserProfiles.getUserdetail().getRegisterdata())
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getUserRoles(HttpServletRequest request) {
        return userRepository.findById(jwtService.getUId(request)).get().getAuthorities();
    }

    @Override
    public String updateShot(String id, MultipartFile file) {
        
        try {
            // 讀取圖片並裁切成正方形
            BufferedImage image = ImageIO.read(file.getInputStream());
            int size = Math.min(image.getWidth(), image.getHeight());
            int x = (image.getWidth() - size) / 2;
            int y = (image.getHeight() - size) / 2;
            BufferedImage cropped = image.getSubimage(x, y, size, size);
            
            // 將裁切後的圖片轉換為 byte 陣列
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(cropped, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            
            // 上傳到 Google Cloud Storage
            return GoogleFileUtil.uploadFile(id, new ByteArrayMultipartFile(bytes, file.getOriginalFilename(), file.getContentType()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
