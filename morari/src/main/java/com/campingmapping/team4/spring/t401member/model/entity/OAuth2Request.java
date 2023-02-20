package com.campingmapping.team4.spring.t401member.model.entity;

import java.util.Optional;

// Oauth2登入後拿取
public record OAuth2Request(
        String email,
        Optional<String> name,
        AuthProvider provider,
        Optional<String> shot) {
}
