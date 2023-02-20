package com.campingmapping.team4.spring.t401member.controller.api;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestEdit;
import com.campingmapping.team4.spring.t401member.model.dto.UserDetailGuestWeb;
import com.campingmapping.team4.spring.t401member.model.service.*;
import com.campingmapping.team4.spring.utils.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("guest/camper/api")
public class GuestUserApi {
    @Autowired
    UserService userService;
    // @Autowired
    // GoogleFileUtil googleFileUtil;
    @Autowired
    JwtService jwtService;

    @GetMapping("/shot")
    @ResponseBody
    public String getShot(HttpServletRequest request) {
        return userService.getShot(request);
    }

    @PutMapping("/shot")
    @ResponseBody
    public String putShot(@RequestParam("uid") String id,
            @RequestParam("file") MultipartFile file)  {
        return userService.updateShot("usershot" + id, file);
    }

    @GetMapping("/nickname")
    @ResponseBody
    public String getNickname(HttpServletRequest request) {
        return userService.getNickname(request);
    }

    @GetMapping("/userdetail/{uid}")
    @ResponseBody
    public UserDetailGuestWeb getUserDetail(@PathVariable("uid") UUID uid) {
        return userService.getUserDetail(uid);
    }

    @PutMapping("/userdetail/{uid}")
    @ResponseBody
    public ResponseEntity<Void> updateUserDetail(@PathVariable("uid") UUID uid,
            @RequestBody UserDetailGuestEdit uesrDetailGuestEdit,
            HttpServletRequest request) {
        if (jwtService.getUId(request).equals(uid)) {
            Boolean saveSuccess = userService.guestUpdateUser(uesrDetailGuestEdit);
            if (saveSuccess) {
                // 成功回傳200
                return ResponseEntity.ok().build();
            } else {
                // 失敗回傳500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            // 修改UID比對錯誤401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
