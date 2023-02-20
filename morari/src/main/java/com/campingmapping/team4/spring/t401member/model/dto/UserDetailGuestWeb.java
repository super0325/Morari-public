package com.campingmapping.team4.spring.t401member.model.dto;

public record UserDetailGuestWeb(
        String uid,
        String nickname,
        Long exp,
        Integer level,
        Long point,
        String shot,
        String about
        ) {}