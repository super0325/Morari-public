package com.campingmapping.team4.spring.utils.service.attributemapper;

import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.entity.AuthProvider;

import java.util.EnumMap;
import java.util.Map;

// 不同提供商配對不同的Mapper
@Component
public class AttributeMapperFactory {
  private final Map<AuthProvider, AttributeMappable> mapperMap = new EnumMap<>(AuthProvider.class);
  private final GoogleAttributeMapper googleAttributeMapper;

  public AttributeMapperFactory(
      GoogleAttributeMapper googleAttributeMapper) {
    this.googleAttributeMapper = googleAttributeMapper;

    initialize();
  }

  private void initialize() {
    mapperMap.put(AuthProvider.GOOGLE, googleAttributeMapper);

  }

  public AttributeMappable get(AuthProvider authProvider) {
    return mapperMap.get(authProvider);
  }
}
