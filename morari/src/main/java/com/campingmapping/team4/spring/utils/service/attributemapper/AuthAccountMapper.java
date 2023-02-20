package com.campingmapping.team4.spring.utils.service.attributemapper;

import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthAccountMapper {
  public Map<String, Object> mapToAttributeMap(UserProfiles user) {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("id", user.getUid());
    attributes.put("email", user.getEmail());

    return attributes;
  }
}
