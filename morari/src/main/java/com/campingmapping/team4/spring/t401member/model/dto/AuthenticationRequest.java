package com.campingmapping.team4.spring.t401member.model.dto;

public record AuthenticationRequest(
    String email,
    String password,
    Boolean rememberMe) {

}
