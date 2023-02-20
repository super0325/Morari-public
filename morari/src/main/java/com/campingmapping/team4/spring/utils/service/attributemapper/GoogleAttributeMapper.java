package com.campingmapping.team4.spring.utils.service.attributemapper;

import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.entity.AuthProvider;
import com.campingmapping.team4.spring.t401member.model.entity.OAuth2Request;

import java.util.Map;
import java.util.Optional;

@Component
public class GoogleAttributeMapper implements AttributeMappable {
  @Override
  public OAuth2Request mapToDTO(Map<String, Object> attributes) {
    String email = (String) attributes.get("email");
    String name = (String) attributes.get("name");
    String shot = (String) attributes.get("picture");
    return new OAuth2Request(email, Optional.ofNullable(name), AuthProvider.GOOGLE, Optional.ofNullable(shot));
  }
}
