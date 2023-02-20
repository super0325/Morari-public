package com.campingmapping.team4.spring.t401member.model.dto;

import java.util.UUID;

// OAUTH2 的登入USER
public record OAuth2Login(UUID id, String email) {}

