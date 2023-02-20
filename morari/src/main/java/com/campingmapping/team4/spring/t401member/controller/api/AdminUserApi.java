package com.campingmapping.team4.spring.t401member.controller.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t401member.model.dto.UserDetailAdminWeb;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.service.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/camper/api")
public class AdminUserApi {
    @Autowired
    UserService userService;

    @GetMapping("/showall")
    @ResponseBody
    public List<UserDetailAdminWeb> getAllUser() {
        return userService.showAllUser();
    }

    // Admin update user
    @PutMapping("/user")
    @ResponseBody
    public ResponseEntity<Void> updateUser(@RequestBody UserDetailAdminWeb user) {

        Boolean saveSuccess = userService.adminUpdateUser(user);
        if (saveSuccess) {
            // 成功回傳200
            return ResponseEntity.ok().build();
        } else {
            // 失敗回傳500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/accountlocked/{uid}")
    @ResponseBody
    public ResponseEntity<Void> accountlocked(@PathVariable("uid") UUID uid,
            @RequestBody Boolean accountnonlocked) {

        Boolean saveSuccess = userService.updateaccountlocked(uid, accountnonlocked);
        if (saveSuccess) {
            // 成功回傳200
            return ResponseEntity.ok().build();
        } else {
            // 失敗回傳500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/enabled/{uid}")
    @ResponseBody
    public ResponseEntity<Void> enabled(@PathVariable("uid") UUID uid, @RequestBody Boolean isenabled) {
        Boolean saveSuccess = userService.updateenabled(uid, isenabled);
        if (saveSuccess) {
            // 成功回傳200
            return ResponseEntity.ok().build();
        } else {
            // 失敗回傳500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/guestdetail")
    @ResponseBody
    public UserDetailGuestEdit getUesrDetailGuestEdit(HttpServletRequest request) {
        return userService.getUesrDetailGuestEdit(request);
    }
    @GetMapping("/userregisterdate")
    @ResponseBody
    public List<Date> getUserRegisterDate() {
        return userService.getUserRegisterDate();
    }
    @GetMapping("/userroles")
    @ResponseBody
    public Collection<? extends GrantedAuthority> getuserroles(HttpServletRequest request) {
        return userService.getUserRoles(request);
    }



}
