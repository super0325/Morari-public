package com.campingmapping.team4.spring.utils.service.attributemapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.entity.AuthProvider;
import com.campingmapping.team4.spring.t401member.model.entity.OAuth2Request;

import java.util.Map;

// 將OAUTH拿到的東西轉換成DTO
@Component
@RequiredArgsConstructor
public class AttributeMapper {

  private final AttributeMapperFactory attributeMapperFactory;

  public OAuth2Request mapToUser(AuthProvider provider, Map<String, Object> attributes) {
    return attributeMapperFactory.get(provider).mapToDTO(attributes);
  }
}
