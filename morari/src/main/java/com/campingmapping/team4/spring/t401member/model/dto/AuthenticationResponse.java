package com.campingmapping.team4.spring.t401member.model.dto;

public record AuthenticationResponse (
  String accessToken,
  String refreshToken,
  String grantType
){}
