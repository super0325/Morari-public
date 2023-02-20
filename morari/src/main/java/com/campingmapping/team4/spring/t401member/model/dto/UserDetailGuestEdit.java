package com.campingmapping.team4.spring.t401member.model.dto;

import java.util.Date;

public record UserDetailGuestEdit(
                String uid,
                String nickname,
                String firstname,
                String lastname,
                String email,
                String phone,
                Date birthday,
                String address,
                Integer gender,
                Long exp,
                Integer level,
                Long point,
                Boolean subscribed,
                String shot,
                String about) {

}